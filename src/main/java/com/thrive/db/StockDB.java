package com.thrive.db;

import com.thrive.model.dao.StoredStock;

import java.util.List;
import java.util.Optional;

public interface StockDB {
    List<StoredStock> getAllStocks();
    Optional<StoredStock> getStock(String stockId) throws Exception;
    Optional<StoredStock> save(StoredStock storedStock) throws Exception;
}
