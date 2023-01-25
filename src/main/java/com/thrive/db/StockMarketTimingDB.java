package com.thrive.db;

import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredStockMarketTiming;

import java.util.List;
import java.util.Optional;

public interface StockMarketTimingDB {
    Optional<StoredStockMarketTiming> getStockMarketTiming() throws Exception;
    Optional<StoredStockMarketTiming> save(StoredStockMarketTiming storedStockMarketTiming) throws Exception;
}
