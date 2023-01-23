package com.thrive.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thrive.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private String transactionId;
    private Integer amount;
    private Integer unit;

    private String userId;
    private TransactionType type;
    private String stockId;

    private Date createdAt;
    private Date updatedAt;
}
