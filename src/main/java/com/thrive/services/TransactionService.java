package com.thrive.services;

import com.thrive.model.dto.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getUserTransactions(String email) throws Exception;
}
