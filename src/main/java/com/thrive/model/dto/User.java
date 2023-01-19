package com.thrive.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thrive.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    private String name;
    private String password;
    private String email;

    private UserType type;
    private Date updatedAt;
    private Date createdAt;
}
