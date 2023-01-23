package com.thrive.model.dto;

import com.thrive.model.TransactionType;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private Integer amount;
    private TransactionType type;
    private String stockId;

    private Date createdAt;
    private Date updatedAt;
}
