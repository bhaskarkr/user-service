package com.thrive.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.StockDB;
import com.thrive.model.UserType;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dto.Stock;
import com.thrive.model.dto.User;
import com.thrive.model.request.CreateStockRequest;
import com.thrive.model.request.UpdateStockPriceRequest;
import com.thrive.services.StockService;
import com.thrive.services.UserService;
import com.thrive.util.StockUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class StockServiceImpl implements StockService {
    private final StockDB stockDB;
    private final UserService userService;

    @Inject
    public StockServiceImpl(StockDB stockDB, UserService userService) {
        this.stockDB = stockDB;
        this.userService = userService;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockDB.getAllStocks().stream().map(StockUtils::toDto).collect(Collectors.toList());
    }

    @Override
    public Stock getStock(String stockId) throws Exception {
        Optional<StoredStock> optionalStoredStock = stockDB.getStock(stockId);
        if(!optionalStoredStock.isPresent()){
            throw new UserException(ErrorCode.STOCK_DOES_NOT_EXIST, "Stock doesn't exist");
        };
        return StockUtils.toDto(optionalStoredStock.get());
    }

    @Override
    public Stock create(CreateStockRequest createStockRequest) throws Exception {
        User user = userService.getUserByEmail(createStockRequest.getEmail(), true);
        if(!UserType.ADMIN.equals(user.getType()))
            throw new UserException(ErrorCode.ONLY_ADMIN_CAN_UPDATE_PRICE, "Only Admin are allowed");
        Optional<StoredStock> optionalStoredStock = stockDB.save(StockUtils.toDao(createStockRequest));
        if(!optionalStoredStock.isPresent()){
            throw new UserException(ErrorCode.STOCK_NOT_SAVED, "Stock not saved");
        }
        return StockUtils.toDto(optionalStoredStock.get());
    }

    @Override
    public void updatePrice(UpdateStockPriceRequest updateStockPrice) throws Exception {
        User user = userService.getUserByEmail(updateStockPrice.getEmail(), true);
        if(!UserType.ADMIN.equals(user.getType()))
            throw new UserException(ErrorCode.ONLY_ADMIN_CAN_UPDATE_PRICE, "Only Admin are allowed");
        Optional<StoredStock> existingStock = stockDB.getStock(updateStockPrice.getStockId());
        if(!existingStock.isPresent()){
            throw new UserException(ErrorCode.STOCK_DOES_NOT_EXIST, "Stock doesn't exist");
        }
        existingStock.get().setPreviousPrice(existingStock.get().getCurrentPrice());
        existingStock.get().setDayHigh(Math.max(existingStock.get().getCurrentPrice(), existingStock.get().getDayHigh()));
        existingStock.get().setDayLow(Math.min(existingStock.get().getCurrentPrice(), existingStock.get().getDayLow()));
        existingStock.get().setPreviousPrice(updateStockPrice.getPrice());
        stockDB.save(existingStock.get());
    }
}
