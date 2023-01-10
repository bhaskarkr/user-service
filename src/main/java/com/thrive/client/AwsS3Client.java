package com.thrive.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.config.AWSCredential;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import java.util.Objects;
@Slf4j
@Singleton
public class AwsS3Client implements Managed {
    private S3Client s3Client;
    private final AWSCredential AWSCredential;

    @Inject
    public AwsS3Client(AWSCredential AWSCredential) {
        this.AWSCredential = AWSCredential;
    }

    private S3Client getS3TransferManager() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                AWSCredential.getAccessKeyId(),
                AWSCredential.getSecretAccessKey());
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.US_EAST_1)
                .build();
    }

    public S3Client getClient() {
        if(Objects.isNull(s3Client))
            s3Client = getS3TransferManager();
        return s3Client;
    }

    @Override
    public void start() throws Exception {
        s3Client = getClient();
    }

    @Override
    public void stop() throws Exception {
        if (Objects.nonNull(s3Client))
            s3Client.close();
    }
}
