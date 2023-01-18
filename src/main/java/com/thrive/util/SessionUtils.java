package com.thrive.util;

import com.thrive.model.dto.User;
import com.thrive.model.response.LoginSuccessResponse;

import java.util.Arrays;
import java.util.List;

public interface SessionUtils {
    static LoginSuccessResponse toLoginSuccessResponse(User user, List<String> stocks) {
        return LoginSuccessResponse.builder()
                .stocks(Arrays.asList("TATA MOTORS", "HCL", "INFOSYS", "MAMA-EARTH"))
                .user(user)
                .build();
    }
}
