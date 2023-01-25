package com.thrive.util;

import com.thrive.model.dao.StoredStockMarketTiming;
import com.thrive.model.dto.StockMarketTiming;
import com.thrive.model.request.CreateStockMarketTimingRequest;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface StockMarketTimingUtils {
    static StockMarketTiming dto(StoredStockMarketTiming storedStockMarketTiming) {
        return StockMarketTiming.builder()
                .endTime(storedStockMarketTiming.getEndTime())
                .startTime(storedStockMarketTiming.getStartTime())
                .build();

    }
    static StoredStockMarketTiming dao(CreateStockMarketTimingRequest request) {
        return StoredStockMarketTiming.builder()
                .id(IdGenerator.generate("SM").getId())
                .endTime(request.getEndTime())
                .startTime(request.getStartTime())
                .build();
    }
}
