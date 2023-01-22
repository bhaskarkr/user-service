package com.thrive.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.UsersDB;
import com.thrive.db.WalletDB;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredWallet;
import com.thrive.model.dto.Wallet;
import com.thrive.model.request.CreateUserWalletRequest;
import com.thrive.services.WalletService;
import com.thrive.util.WalletUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Singleton
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletDB walletDB;
    private final UsersDB usersDB;

    @Inject
    public WalletServiceImpl(WalletDB walletDB, UsersDB usersDB) {
        this.walletDB = walletDB;
        this.usersDB = usersDB;
    }

    @Override
    public Wallet getWallet(String email, boolean allowInactive) throws Exception {
        Optional<StoredUser> optionalStoredUser = usersDB.getUserByEmail(email, true);
        if(optionalStoredUser.isEmpty()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "user doesn't exist");
        }
        Optional<StoredWallet> optionalStoredWallet = walletDB.get(optionalStoredUser.get(), true);
        if(!optionalStoredWallet.isPresent()) {
            throw new UserException(ErrorCode.WALLET_NOT_FOUND, "wallet not saved");
        }
        return WalletUtils.dto(optionalStoredWallet.get());
    }

    @Override
    public Wallet createWallet(CreateUserWalletRequest request) throws Exception {
        Optional<StoredUser> optionalStoredUser = usersDB.getUserByEmail(request.getEmail(), true);
        if(optionalStoredUser.isEmpty()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "user doesn't exist");
        }
        Optional<StoredWallet> optionalStoredWallet = walletDB.get(optionalStoredUser.get(), true);
        if(optionalStoredWallet.isPresent()) {
            throw new UserException(ErrorCode.WALLET_ALREADY_EXIST, "wallet already exist for user");
        }
        Optional<StoredWallet> savedStoredWallet = walletDB.save(WalletUtils.dao(request, optionalStoredUser.get()));
        if(savedStoredWallet.isEmpty()){
            throw new UserException(ErrorCode.WALLET_NOT_SAVED, "wallet not saved");
        }
        return WalletUtils.dto(savedStoredWallet.get());
    }
}
