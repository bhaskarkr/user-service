package com.thrive.services;

import com.thrive.model.dto.StockMarketTiming;
import com.thrive.model.request.CreateStockMarketTimingRequest;

public interface StockMarketTimingService {
    StockMarketTiming getStockMarketTiming() throws Exception;

    void addMarketTiming(CreateStockMarketTimingRequest request)throws Exception;

    void validateMarketTiming()throws Exception;
 }
