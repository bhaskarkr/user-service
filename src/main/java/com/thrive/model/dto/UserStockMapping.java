package com.thrive.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStockMapping {
    private String id;
    private User user;
    private Stock stock;

    private Integer totalAmount;
    private Integer totalUnit;
}
