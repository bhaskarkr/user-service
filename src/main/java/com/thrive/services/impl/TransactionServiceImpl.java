package com.thrive.services.impl;

import com.google.inject.Inject;
import com.thrive.db.TransactionDB;
import com.thrive.model.dto.Transaction;
import com.thrive.model.dto.User;
import com.thrive.services.TransactionService;
import com.thrive.services.UserService;
import com.thrive.util.TransactionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDB transactionDB;
    private final UserService userService;

    @Inject
    public TransactionServiceImpl(TransactionDB transactionDB, UserService userService) {
        this.transactionDB = transactionDB;
        this.userService = userService;
    }

    @Override
    public List<Transaction> getUserTransactions(String email) throws Exception {
        User user = userService.getUserByEmail(email, true);
        return transactionDB.getUserTransactions(user).stream().map(TransactionUtils::dto).collect(Collectors.toList());
    }
}
