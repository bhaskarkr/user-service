package com.thrive.services.impl;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.Stock;
import com.thrive.model.dto.User;
import com.thrive.model.request.LoginRequest;
import com.thrive.model.response.LoginSuccessResponse;
import com.thrive.services.SessionService;
import com.thrive.services.StockService;
import com.thrive.services.UserService;
import com.thrive.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Singleton
@Slf4j
public class SessionServiceImpl implements SessionService {
    private final UserService userService;
    private final StockService stockService;

    @Inject
    public SessionServiceImpl(UserService userService, StockService stockService) {
        this.userService = userService;
        this.stockService = stockService;
    }

    @Override
    public LoginSuccessResponse login(LoginRequest loginRequest) throws Exception {
        User user = userService.getUser(loginRequest.getEmail(), loginRequest.getPassword(), true);
        List<Stock> stocks = stockService.getAllStocks();
        return SessionUtils.toLoginSuccessResponse(user, stocks);
    }
}
