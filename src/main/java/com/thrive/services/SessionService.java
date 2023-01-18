package com.thrive.services;

import com.thrive.model.request.LoginRequest;
import com.thrive.model.response.LoginSuccessResponse;

public interface SessionService {
    LoginSuccessResponse login(LoginRequest loginRequest) throws Exception;
}
