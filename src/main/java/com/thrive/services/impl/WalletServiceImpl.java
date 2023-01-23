package com.thrive.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.TransactionDB;
import com.thrive.db.UsersDB;
import com.thrive.db.WalletDB;
import com.thrive.model.TransactionType;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredWallet;
import com.thrive.model.dto.Transaction;
import com.thrive.model.dto.Wallet;
import com.thrive.model.request.CreateUserWalletRequest;
import com.thrive.model.request.UpdateWalletAmountRequest;
import com.thrive.services.WalletService;
import com.thrive.util.TransactionUtils;
import com.thrive.util.UserUtils;
import com.thrive.util.WalletUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Singleton
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletDB walletDB;
    private final UsersDB usersDB;
    private final TransactionDB transactionDB;

    @Inject
    public WalletServiceImpl(WalletDB walletDB, UsersDB usersDB, TransactionDB transactionDB) {
        this.walletDB = walletDB;
        this.usersDB = usersDB;
        this.transactionDB = transactionDB;
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

    @Override
    public Wallet updateWalletAmount(UpdateWalletAmountRequest request) throws Exception {
        Optional<StoredUser> optionalStoredUser = usersDB.getUserByEmail(request.getEmail(), true);
        if(optionalStoredUser.isEmpty()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "user doesn't exist");
        }
        Optional<StoredWallet> optionalStoredWallet = walletDB.get(optionalStoredUser.get(), true);
        if(optionalStoredWallet.isEmpty()) {
            throw new UserException(ErrorCode.WALLET_NOT_FOUND, "wallet not found for user");
        }
        Integer newAmount = optionalStoredWallet.get().getAmount() + request.getAmount();
        if(newAmount < 0) {
            throw new UserException(ErrorCode.WALLET_INSUFFICIENT_BALANCE, "wallet balance is not sufficient");
        }
        optionalStoredWallet.get().setAmount(newAmount);
        Optional<StoredWallet> savedStoredWallet = walletDB.save(optionalStoredWallet.get());
        transactionDB.save(UserUtils.toDto(optionalStoredUser.get()),
                TransactionUtils.dao(Transaction.builder()
                                .amount(request.getAmount() < 0 ? request.getAmount() * -1 : request.getAmount())
                                .userId(optionalStoredUser.get().getId())
                                .type(request.getAmount() < 0 ? TransactionType.WITHDRAW : TransactionType.DEPOSIT)
                                .build()));
        return WalletUtils.dto(savedStoredWallet.get());
    }

}
