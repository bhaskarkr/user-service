package com.thrive.db;

import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredUserStockMapping;

import java.util.List;
import java.util.Optional;


public interface UserStockMappingDB {

    Optional<StoredUserStockMapping> get(StoredUser storedUser, StoredStock storedStock, boolean allowInactive) throws Exception;
    List<StoredUserStockMapping> get(StoredUser storedUser, boolean allowInactive) throws Exception;
    Optional<StoredUserStockMapping> save(StoredUserStockMapping storedUserStockMapping) throws Exception;

}
