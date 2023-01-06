package com.thrive.services;

import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;

public interface UserService {

    User getUser(String userId, boolean allowInactive) throws Exception;

    User createUser(UserCreateRequest request) throws Exception;
}
