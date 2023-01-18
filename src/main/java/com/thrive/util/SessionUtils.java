package com.thrive.util;

import com.thrive.model.dto.Stock;
import com.thrive.model.dto.User;
import com.thrive.model.response.LoginSuccessResponse;

import java.util.List;

public interface SessionUtils {
    static LoginSuccessResponse toLoginSuccessResponse(User user, List<Stock> stocks) {
        return LoginSuccessResponse.builder()
                .stocks(stocks)
                .user(user)
                .build();
    }
}
