package com.thrive.services;

import com.thrive.model.dto.Wallet;
import com.thrive.model.request.CreateUserWalletRequest;
import com.thrive.model.request.UpdateWalletAmountRequest;

public interface WalletService {
    Wallet getWallet(final String email, boolean allowInactive) throws Exception;

    Wallet createWallet(final CreateUserWalletRequest request) throws Exception;

    Wallet updateWalletAmount(UpdateWalletAmountRequest request) throws Exception;
}
