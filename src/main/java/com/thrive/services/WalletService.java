package com.thrive.services;

import com.thrive.model.dto.User;
import com.thrive.model.dto.Wallet;
import com.thrive.model.request.CreateUserWalletRequest;

public interface WalletService {
    Wallet getWallet(final String email, boolean allowInactive) throws Exception;

    Wallet createWallet(CreateUserWalletRequest request) throws Exception;
}
