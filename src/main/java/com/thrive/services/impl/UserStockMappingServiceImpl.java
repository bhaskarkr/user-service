package com.thrive.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.StockDB;
import com.thrive.db.UserStockMappingDB;
import com.thrive.db.UsersDB;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredUserStockMapping;
import com.thrive.model.dto.Stock;
import com.thrive.model.dto.User;
import com.thrive.model.dto.UserStockMapping;
import com.thrive.model.dto.Wallet;
import com.thrive.model.request.UpdateWalletAmountRequest;
import com.thrive.model.request.UserStockMappingRequest;
import com.thrive.services.UserStockMappingService;
import com.thrive.services.WalletService;
import com.thrive.util.UserStockMappingUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class UserStockMappingServiceImpl implements UserStockMappingService {

    private final UsersDB usersDB;
    private final StockDB stockDB;
    private final WalletService walletService;
    private final UserStockMappingDB userStockMappingDB;

    @Inject
    public UserStockMappingServiceImpl(UsersDB usersDB,
                                       StockDB stockDB,
                                       WalletService walletService,
                                       UserStockMappingDB userStockMappingDB) {
        this.usersDB = usersDB;
        this.stockDB = stockDB;
        this.walletService = walletService;
        this.userStockMappingDB = userStockMappingDB;
    }

    @Override
    public List<UserStockMapping> getUserStockMapping(String email) throws Exception {
        Optional<StoredUser> optionalStoredUser = usersDB.getUserByEmail(email, true);
        if(!optionalStoredUser.isPresent()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "User not Found");
        }
        List<StoredUserStockMapping> storedUserStockMappingList = userStockMappingDB.get(optionalStoredUser.get(), true);
        return storedUserStockMappingList.stream().map(UserStockMappingUtils::dto).collect(Collectors.toList());
    }

    @Override
    public UserStockMapping getUserStockMapping(String email, String stockId) throws Exception {
        Optional<StoredUser> optionalStoredUser = usersDB.getUserByEmail(email, true);
        if(!optionalStoredUser.isPresent()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "User not Found");
        }
        Optional<StoredStock> optionalStoredStock = stockDB.getStock(stockId);
        if(!optionalStoredStock.isPresent()) {
            throw new UserException(ErrorCode.STOCK_DOES_NOT_EXIST, "Stock not Found");
        }
        Optional<StoredUserStockMapping> storedUserStockMappingList = userStockMappingDB.get(optionalStoredUser.get(), optionalStoredStock.get(), true);
        return UserStockMappingUtils.dto(storedUserStockMappingList.get());
    }

    @Override
    public UserStockMapping buyStockUserStockMapping(UserStockMappingRequest request) throws Exception {
        Optional<StoredUser> optionalStoredUser = usersDB.getUserByEmail(request.getEmail(), true);
        if(!optionalStoredUser.isPresent()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "User not Found");
        }

        Optional<StoredStock> optionalStoredStock = stockDB.getStock(request.getStockId());
        if(!optionalStoredStock.isPresent()) {
            throw new UserException(ErrorCode.STOCK_DOES_NOT_EXIST, "Stock not Found");
        }

        Optional<StoredUserStockMapping> optionalStoredUserStockMapping = userStockMappingDB.get(optionalStoredUser.get(), optionalStoredStock.get(), true);
        if(optionalStoredUserStockMapping.isEmpty()) {
            optionalStoredUserStockMapping = Optional.of(UserStockMappingUtils.dao(optionalStoredStock.get(), optionalStoredUser.get()));
        }
        if(optionalStoredStock.get().getAvailableUnit() < request.getUnit()) {
            throw new UserException(ErrorCode.NOT_ENOUGH_STOCK_AVAILABLE, "Not Stock units are available for the transaction");
        }
        Integer newAllotmentCost = request.getUnit() * optionalStoredStock.get().getCurrentPrice();
        Integer newUnitAvailableCount = optionalStoredUserStockMapping.get().getTotalUnit() + request.getUnit();
        Integer newTotalAmount = optionalStoredUserStockMapping.get().getTotalAmount() + newAllotmentCost;
        walletService.updateWalletAmount(UpdateWalletAmountRequest.builder()
                .amount(-newAllotmentCost)
                .email(request.getEmail())
                .build());
        optionalStoredUserStockMapping.get().setTotalUnit(newUnitAvailableCount);
        optionalStoredUserStockMapping.get().setTotalAmount(newTotalAmount);
        Optional<StoredUserStockMapping> updatedStoredUserStockMapping = userStockMappingDB.save(optionalStoredUserStockMapping.get());
        optionalStoredStock.get().setAvailableUnit(optionalStoredStock.get().getAvailableUnit() - request.getUnit());
        stockDB.save(optionalStoredStock.get());
        return UserStockMappingUtils.dto(updatedStoredUserStockMapping.get());
    }

    @Override
    public UserStockMapping sellStockUserStockMapping(UserStockMappingRequest request) throws Exception {
        Optional<StoredUser> optionalStoredUser = usersDB.getUserByEmail(request.getEmail(), true);
        if(!optionalStoredUser.isPresent()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "User not Found");
        }

        Optional<StoredStock> optionalStoredStock = stockDB.getStock(request.getStockId());
        if(!optionalStoredStock.isPresent()) {
            throw new UserException(ErrorCode.STOCK_DOES_NOT_EXIST, "Stock not Found");
        }

        Optional<StoredUserStockMapping> optionalStoredUserStockMapping = userStockMappingDB.get(optionalStoredUser.get(), optionalStoredStock.get(), true);
        if(optionalStoredUserStockMapping.isEmpty()) {
            optionalStoredUserStockMapping = Optional.of(UserStockMappingUtils.dao(optionalStoredStock.get(), optionalStoredUser.get()));
        }
        Integer sellingAllotmentCost = request.getUnit() * optionalStoredStock.get().getCurrentPrice();
        Integer newUnitAvailableCount = optionalStoredUserStockMapping.get().getTotalUnit() - request.getUnit();
        Integer newTotalAmount = optionalStoredUserStockMapping.get().getTotalAmount() - sellingAllotmentCost;
        walletService.updateWalletAmount(UpdateWalletAmountRequest.builder()
                .amount(sellingAllotmentCost)
                .email(request.getEmail())
                .build());
        optionalStoredUserStockMapping.get().setTotalUnit(newUnitAvailableCount);
        optionalStoredUserStockMapping.get().setTotalAmount(newTotalAmount);

        optionalStoredStock.get().setAvailableUnit(optionalStoredStock.get().getAvailableUnit() + request.getUnit());
        stockDB.save(optionalStoredStock.get());

        Optional<StoredUserStockMapping> updatedStoredUserStockMapping = userStockMappingDB.save(optionalStoredUserStockMapping.get());
        return UserStockMappingUtils.dto(updatedStoredUserStockMapping.get());
    }
}
