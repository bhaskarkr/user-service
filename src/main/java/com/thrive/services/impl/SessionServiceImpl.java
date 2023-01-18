package com.thrive.services.impl;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.core.UserException;
import com.thrive.model.dto.User;
import com.thrive.model.request.LoginRequest;
import com.thrive.model.response.LoginSuccessResponse;
import com.thrive.services.SessionService;
import com.thrive.services.UserService;
import com.thrive.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class SessionServiceImpl implements SessionService {
    private final UserService userService;

    @Inject
    public SessionServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public LoginSuccessResponse login(LoginRequest loginRequest) throws Exception {
        User user = userService.getUser(loginRequest.getEmail(), loginRequest.getPassword(), true);
        return SessionUtils.toLoginSuccessResponse(user, ImmutableList.of());
    }
}
