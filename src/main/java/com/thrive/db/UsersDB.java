package com.thrive.db;

import com.thrive.model.dao.StoredUser;

import java.util.Optional;
public interface UsersDB {

    Optional<StoredUser> get(String userId, boolean allowInactive) throws Exception;

    Optional<StoredUser> save(StoredUser storedBase) throws Exception;

}
