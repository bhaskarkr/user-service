package com.thrive.util;

import com.thrive.model.dao.StoredStockMarketTiming;
import com.thrive.model.dto.StockMarketTiming;
import com.thrive.model.request.CreateStockMarketTimingRequest;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface StockMarketTimingUtils {
    static StockMarketTiming dto(StoredStockMarketTiming storedStockMarketTiming) {
        return StockMarketTiming.builder()
                .endTimeMinutes(storedStockMarketTiming.getEndTimeInMinutes())
                .startTimeInMinutes(storedStockMarketTiming.getStartTimeInMinutes())
                .build();

    }
    static StoredStockMarketTiming dao(CreateStockMarketTimingRequest request) {
        return StoredStockMarketTiming.builder()
                .id(IdGenerator.generate("SM").getId())
                .endTimeInMinutes(request.getEndTimeInMinutes())
                .startTimeInMinutes(request.getStartTimeInMinutes())
                .build();
    }
}
