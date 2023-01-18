package com.thrive.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thrive.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginSuccessResponse {
    private List<String> stocks;
    private User user;
}
