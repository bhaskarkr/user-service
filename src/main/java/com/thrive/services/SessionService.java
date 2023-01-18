package com.thrive.services;

import com.thrive.model.request.LoginRequest;
import com.thrive.model.response.LoginResponse;

public interface SessionService {
    LoginResponse login(LoginRequest loginRequest) throws Exception;
}
