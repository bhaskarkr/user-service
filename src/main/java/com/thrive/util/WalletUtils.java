package com.thrive.util;

import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredWallet;
import com.thrive.model.dto.Wallet;
import com.thrive.model.request.CreateUserWalletRequest;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface WalletUtils {
    static Wallet dto(StoredWallet storedWallet) {
        return Wallet.builder()
                .accountNumber(storedWallet.getAccountNumber())
                .id(storedWallet.getId())
                .amount(storedWallet.getAmount())
                .createdAt(storedWallet.getCreatedAt())
                .updatedAt(storedWallet.getUpdatedAt())
                .build();
    }

    static StoredWallet dao(CreateUserWalletRequest createUserWalletRequest, StoredUser storedUser) {
        return StoredWallet.builder()
                .accountNumber(createUserWalletRequest.getAccountNumber())
                .user(storedUser)
                .active(true)
                .id(IdGenerator.generate("W").getId())
                .amount(0)
                .build();
    }
}
