package com.thrive.db.impl;

import com.google.inject.Inject;
import com.thrive.client.AwsS3Client;
import com.thrive.db.UserFileS3;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectAclRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.Optional;

import static com.thrive.constant.Constants.BUCKET_NAME;

public class UserFileS3Impl implements UserFileS3 {
    private final S3Client s3Client;

    @Inject
    public UserFileS3Impl(AwsS3Client awsS3Client) {
        this.s3Client = awsS3Client.getClient();
    }

    @Override
    public Optional<File> get(String userId, String fileName) throws Exception {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();
        GetObjectResponse response = s3Client.getObject(request).response();
        return Optional.empty();
    }

    @Override
    public boolean put(String userId, File file) throws Exception {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(file.getName())
                .build();
        s3Client.putObject(request, RequestBody.fromFile(file));
        return true;
    }
}
