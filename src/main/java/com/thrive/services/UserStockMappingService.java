package com.thrive.services;

import com.thrive.model.dto.UserStockMapping;
import com.thrive.model.request.UserStockMappingRequest;

import java.util.List;

public interface UserStockMappingService {
    List<UserStockMapping> getUserStockMapping(final String email) throws Exception;

    UserStockMapping getUserStockMapping(final String email,  final String stockId) throws Exception;

    UserStockMapping buyStockUserStockMapping(final UserStockMappingRequest request) throws Exception;
    UserStockMapping sellStockUserStockMapping(final UserStockMappingRequest request) throws Exception;
}
