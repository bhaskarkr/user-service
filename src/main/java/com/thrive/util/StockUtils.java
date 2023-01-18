package com.thrive.util;

import com.thrive.model.dao.StoredStock;
import com.thrive.model.dto.Stock;
import com.thrive.model.request.CreateStockRequest;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface StockUtils {
    static StoredStock toDao(CreateStockRequest request) {
        return StoredStock.builder()
                .id(IdGenerator.generate("S").getId())
                .active(true)
                .name(request.getName())
                .dayLow(request.getPrice())
                .dayHigh(request.getPrice())
                .previousPrice(request.getPrice())
                .currentPrice(request.getPrice())
                .availableUnit(request.getUnits())
                .build();
    }

    static Stock toDto(StoredStock storedStock) {
        return Stock.builder()
                .stockId(storedStock.getId())
                .name(storedStock.getName())
                .dayLow(storedStock.getDayLow())
                .dayHigh(storedStock.getDayHigh())
                .previousPrice(storedStock.getPreviousPrice())
                .currentPrice(storedStock.getCurrentPrice())
                .availableUnit(storedStock.getAvailableUnit())
                .build();
    }
}
