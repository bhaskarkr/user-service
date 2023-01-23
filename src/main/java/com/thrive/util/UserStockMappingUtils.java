package com.thrive.util;

import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredUserStockMapping;
import com.thrive.model.dto.UserStockMapping;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface UserStockMappingUtils {
    static UserStockMapping dto(StoredUserStockMapping storedUserStockMapping) {
        return UserStockMapping.builder()
                .user(UserUtils.toDto(storedUserStockMapping.getUser()))
                .stock(StockUtils.toDto(storedUserStockMapping.getStock()))
                .id(storedUserStockMapping.getId())
                .totalAmount(storedUserStockMapping.getTotalAmount())
                .totalUnit(storedUserStockMapping.getTotalUnit())
                .build();
    }

    static StoredUserStockMapping dao(StoredStock storedStock,
                                      StoredUser storedUser) {
        return StoredUserStockMapping.builder()
                .user(storedUser)
                .stock(storedStock)
                .id(IdGenerator.generate("SU").getId())
                .totalAmount(0)
                .totalUnit(0)
                .build();
    }
}
