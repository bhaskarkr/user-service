package com.thrive.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStockPriceRequest {

    @NotNull
    private String email;
    @NotNull
    private String stockId;
    @NotNull
    private Integer price;

}