package com.thrive.model.request;

import com.thrive.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    @NotNull
    private String fullName;
    @NotNull
    private String email;

    @NotNull
    private UserType type;

    @NotNull
    private String password;

}
