package com.thrive.util;

import com.thrive.model.dao.StoredTransaction;
import com.thrive.model.dto.Transaction;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface TransactionUtils {
    static Transaction dto(StoredTransaction storedTransaction) {
        return Transaction.builder()
                .transactionId(storedTransaction.getId())
                .amount(storedTransaction.getAmount())
                .createdAt(storedTransaction.getCreatedAt())
                .unit(storedTransaction.getUnit())
                .userId(storedTransaction.getUserId())
                .updatedAt(storedTransaction.getUpdatedAt())
                .stockId(storedTransaction.getStockId())
                .type(storedTransaction.getType())
                .build();

    }

    static StoredTransaction dao(Transaction transaction) {
        return StoredTransaction.builder()
                .id(IdGenerator.generate("T").getId())
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .stockId(transaction.getStockId())
                .type(transaction.getType())
                .unit(transaction.getUnit())
                .userId(transaction.getUserId())
                .build();

    }
}
