package com.thrive.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.StockDB;
import com.thrive.db.StockMarketTimingDB;
import com.thrive.model.dao.StoredStockMarketTiming;
import com.thrive.model.dto.StockMarketTiming;
import com.thrive.model.request.CreateStockMarketTimingRequest;
import com.thrive.services.StockMarketTimingService;
import com.thrive.services.UserService;
import com.thrive.util.StockMarketTimingUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Singleton
@Slf4j
public class StockMarketTimingServiceImpl implements StockMarketTimingService {
    private final StockMarketTimingDB stockMarketTimingDB;

    @Inject
    public StockMarketTimingServiceImpl(StockMarketTimingDB stockMarketTimingDB) {
        this.stockMarketTimingDB = stockMarketTimingDB;
    }

    @Override
    public StockMarketTiming getStockMarketTiming() throws Exception {
        Optional<StoredStockMarketTiming> optionalStoredStockMarketTiming = stockMarketTimingDB.getStockMarketTiming();
        if(optionalStoredStockMarketTiming.isEmpty()) {
            throw new UserException(ErrorCode.MARKET_TIMING_NOT_CREATED, "First create a Market timing");
        }
        return StockMarketTimingUtils.dto(optionalStoredStockMarketTiming.get());
    }

    @Override
    public void addMarketTiming(CreateStockMarketTimingRequest request) throws Exception {
        Optional<StoredStockMarketTiming> optionalStoredStockMarketTiming = stockMarketTimingDB.getStockMarketTiming();
        if(optionalStoredStockMarketTiming.isEmpty()) {
            stockMarketTimingDB.save(StockMarketTimingUtils.dao(request));
        } else {
            optionalStoredStockMarketTiming.get().setEndTime(request.getEndTime());
            optionalStoredStockMarketTiming.get().setStartTime(request.getStartTime());
            stockMarketTimingDB.save(StockMarketTimingUtils.dao(request));
        }
    }
}
