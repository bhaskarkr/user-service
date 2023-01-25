package com.thrive.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStockMarketTimingRequest {

    @NotNull
    private Time startTime;
    @NotNull
    private Time endTime;

}