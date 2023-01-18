package com.thrive.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    private String stockId;
    private String name;
    private Integer availableUnit;
    private Integer currentPrice;
    private Integer dayLow;
    private Integer dayHigh;

    private Integer previousPrice;
}
