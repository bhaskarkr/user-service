package com.thrive.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.StockMarketTimingDB;
import com.thrive.model.UserType;
import com.thrive.model.dao.StoredStockMarketTiming;
import com.thrive.model.dto.StockMarketTiming;
import com.thrive.model.dto.User;
import com.thrive.model.request.CreateStockMarketTimingRequest;
import com.thrive.services.StockMarketTimingService;
import com.thrive.services.UserService;
import com.thrive.util.StockMarketTimingUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.Optional;

@Singleton
@Slf4j
public class StockMarketTimingServiceImpl implements StockMarketTimingService {
    private final StockMarketTimingDB stockMarketTimingDB;
    private final UserService userService;

    @Inject
    public StockMarketTimingServiceImpl(StockMarketTimingDB stockMarketTimingDB, UserService userService) {
        this.stockMarketTimingDB = stockMarketTimingDB;
        this.userService = userService;
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
        User user = userService.getUserByEmail(request.getEmail(), false);
        if(!UserType.ADMIN.equals(user.getType())) {
            throw UserException.error(ErrorCode.ONLY_ADMIN_CAN_UPDATE_MARKET_TIMING, "Only Admin can add/update market timing");
        }
        Optional<StoredStockMarketTiming> optionalStoredStockMarketTiming = stockMarketTimingDB.getStockMarketTiming();
        if(optionalStoredStockMarketTiming.isEmpty()) {
            stockMarketTimingDB.save(StockMarketTimingUtils.dao(request));
        } else {
            optionalStoredStockMarketTiming.get().setEndTimeInMinutes(request.getEndTimeInMinutes());
            optionalStoredStockMarketTiming.get().setStartTimeInMinutes(request.getStartTimeInMinutes());
            stockMarketTimingDB.save(StockMarketTimingUtils.dao(request));
        }
    }

    @Override
    public void validateMarketTiming() throws Exception {
        long now = System.currentTimeMillis();
        Time currTime = new Time(now);
        int hours = currTime.getHours();
        int minutes = currTime.getMinutes();
        int totalMinutes = hours * 60 + minutes;
        StockMarketTiming allowedStockMarketTiming = getStockMarketTiming();
        if(totalMinutes> allowedStockMarketTiming.getEndTimeMinutes() || totalMinutes < allowedStockMarketTiming.getStartTimeInMinutes()) {
            throw new UserException(ErrorCode.BUY_AND_SELL_NOT_ALLOWED_OUTSIDE_MARKET_TIMING, "Market os closed");
        }
    }
}
