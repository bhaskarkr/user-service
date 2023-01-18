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
public class CreateStockRequest {

    @NotNull
    private String name;
    @NotNull
    private Integer units;
    @NotNull
    private Integer price;

}