package com.thrive.client.cache.user;

import com.thrive.model.dto.User;

import java.util.Optional;

public interface UserCache {
    Optional<User> get(String UserId);
    void put(User user);
}
