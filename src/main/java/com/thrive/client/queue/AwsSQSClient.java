package com.thrive.client.queue;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.config.AWSCredential;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.Objects;

@Slf4j
@Singleton
public class AwsSQSClient implements Managed {
    private SqsClient sqsClient;
    private final AWSCredential awsCredential;

    @Inject
    public AwsSQSClient(AWSCredential awsCredential) {
        this.awsCredential = awsCredential;
    }

    private SqsClient getSQSClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                awsCredential.getAccessKeyId(),
                awsCredential.getSecretAccessKey());
        sqsClient = SqsClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.US_EAST_1)
                .build();
        return sqsClient;
    }

    public SqsClient getClient() {
        if(Objects.isNull(sqsClient)) {
            sqsClient = getSQSClient();
        }
        return sqsClient;
    }

    @Override
    public void start() throws Exception {
        this.sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();;
    }

    @Override
    public void stop() throws Exception {
        if(Objects.nonNull(sqsClient))
            sqsClient.close();
    }
}
