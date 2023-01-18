package com.thrive.services;

import com.thrive.db.StockDB;
import com.thrive.db.UsersDB;
import com.thrive.model.dto.Stock;
import com.thrive.model.request.CreateStockRequest;

import java.util.List;

public interface StockService {
    List<Stock> getAllStocks();
    Stock create(CreateStockRequest createStockRequest) throws Exception;
}
