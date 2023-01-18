package com.thrive.services.impl;

import com.google.inject.Inject;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.StockDB;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dto.Stock;
import com.thrive.model.request.CreateStockRequest;
import com.thrive.services.StockService;
import com.thrive.util.StockUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockServiceImpl implements StockService {
    private final StockDB stockDB;

    @Inject
    public StockServiceImpl(StockDB stockDB) {
        this.stockDB = stockDB;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockDB.getAllStocks().stream().map(StockUtils::toDto).collect(Collectors.toList());
    }

    @Override
    public Stock create(CreateStockRequest createStockRequest) throws Exception {
        Optional<StoredStock> optionalStoredStock = stockDB.save(StockUtils.toDao(createStockRequest));
        if(!optionalStoredStock.isPresent()){
            throw new UserException(ErrorCode.STOCK_NOT_SAVED, "Stock not saved");
        }
        return StockUtils.toDto(optionalStoredStock.get());
    }
}
