package com.thrive.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginFailureResponse extends LoginResponse {
    private String message;
    public LoginFailureResponse() {
        super(LoginStatus.INVAILD_CREDENTIALS);
    }
}
