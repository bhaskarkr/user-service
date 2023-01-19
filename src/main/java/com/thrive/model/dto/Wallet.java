package com.thrive.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thrive.model.dao.StoredUser;
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
public class Wallet {
    private String id;
    private String accountNumber;
    private Integer amount;
    private Date updatedAt;
    private Date createdAt;
}
