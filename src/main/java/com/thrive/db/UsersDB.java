package com.thrive.db;

import com.thrive.model.dao.StoredUser;

import java.util.Optional;
public interface UsersDB {

    Optional<StoredUser> get(String email, String password, boolean allowInactive) throws Exception;
    Optional<StoredUser> getUserByEmail(String email, boolean allowInactive) throws Exception;

    Optional<StoredUser> save(StoredUser storedBase) throws Exception;

}
