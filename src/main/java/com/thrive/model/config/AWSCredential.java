package com.thrive.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AWSCredential {
    private String accessKeyId;
    private String secretAccessKey;
}
