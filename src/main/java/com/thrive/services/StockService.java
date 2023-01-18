package com.thrive.services;

import com.thrive.model.dto.Stock;
import com.thrive.model.request.CreateStockRequest;
import com.thrive.model.request.UpdateStockPriceRequest;

import java.util.List;

public interface StockService {
    List<Stock> getAllStocks();
    Stock create(CreateStockRequest createStockRequest) throws Exception;
    void updatePrice(UpdateStockPriceRequest updateStockPrice) throws Exception;
}
