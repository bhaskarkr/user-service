package com.thrive.db;

import com.thrive.model.dao.StoredTransaction;
import com.thrive.model.dto.User;

import java.util.List;
import java.util.Optional;

public interface TransactionDB {
    List<StoredTransaction> getUserTransactions(User user) throws Exception;
    Optional<StoredTransaction> save(User user, StoredTransaction storedTransaction) throws Exception;
}
