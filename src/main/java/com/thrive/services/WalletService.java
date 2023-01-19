package com.thrive.services;

import com.thrive.model.dto.User;
import com.thrive.model.request.CreateUserWalletRequest;
import com.thrive.model.request.UserCreateRequest;

public interface WalletService {
    User getUser(final String email, final String password, boolean allowInactive) throws Exception;

    User createWallet(CreateUserWalletRequest request) throws Exception;
}
