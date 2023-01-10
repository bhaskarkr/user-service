package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.client.queue.AwsSQSClient;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.UserFileS3;
import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;
import com.thrive.services.UserService;
import org.glassfish.jersey.media.multipart.FormDataParam;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/user")
public class UserResource {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    private final UserService userService;
    private final UserFileS3 userFileS3;
    private final SqsClient sqsClient;
    @Inject
    public UserResource(UserService userService, UserFileS3 userFileS3, AwsSQSClient awsSQSClient) {
        this.userService = userService;
        this.userFileS3 = userFileS3;
        this.sqsClient = awsSQSClient.getClient();
    }

    @GET
    @Path("/{userId}")
    public User getUser(@PathParam("userId") String id,
                        @QueryParam("allowInactive") @DefaultValue("false") boolean allowInactive) throws Exception{
        return userService.getUser(id, allowInactive);
    }

    @POST
    @Path("/")
    public User postBase(UserCreateRequest request) throws Exception{
        return userService.createUser(request);
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
    @Path("/upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(@FormDataParam("file") InputStream inputStream,
                             @NotBlank @FormDataParam("user_id") String userId) throws IOException {

        File file = File.createTempFile("USER_" + userId, "_" + System.currentTimeMillis() + ".png");
        try {
            copyInputStreamToFile(inputStream, file);
            userFileS3.put(userId, file);
        } catch (Exception e) {
            throw UserException.error(ErrorCode.DAO_ERROR, "Failed to upload file");
        }
        file.delete();
//        GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName("user-service-sqs-std").build())
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl("https://sqs.us-east-1.amazonaws.com/176712344263/user-service-sqs-std")
                .maxNumberOfMessages(5)
                .build();
        sqsClient.receiveMessage(receiveMessageRequest);
        return "UPLOADED";
    }
}