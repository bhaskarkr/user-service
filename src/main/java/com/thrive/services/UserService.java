package com.thrive.services;

import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;

public interface UserService {

    User getUser(final String email, final String password, boolean allowInactive) throws Exception;

    User createUser(UserCreateRequest request) throws Exception;

    User getUserByEmail(String email, boolean allowInactive) throws Exception;
}
