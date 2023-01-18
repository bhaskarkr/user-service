package com.thrive.services.impl;

import com.google.inject.Singleton;
import com.thrive.client.cache.user.UserCache;
import com.thrive.core.ErrorCode;
import com.thrive.core.UserException;
import com.thrive.db.UsersDB;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;
import com.thrive.services.UserService;
import com.thrive.util.UserUtils;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.util.Optional;

@Singleton
@Slf4j
public class UserServiceImpl implements UserService {

    private final UsersDB usersDB;
    private final UserCache userCache;

    @Inject
    public UserServiceImpl(UsersDB usersDB, UserCache userCache) {
        this.usersDB = usersDB;
        this.userCache = userCache;
    }

    @Override
    public User getUser(String email, String password, boolean allowInactive) throws Exception {
        Optional<User> optionalUser = userCache.get(email);
        if(optionalUser.isPresent()) {
            log.info("Fetched from Cache");
            return optionalUser.get();
        }
        Optional<StoredUser> optionalBase = usersDB.get(email, password, allowInactive);
        if(!optionalBase.isPresent()) {
            throw new UserException(ErrorCode.USER_ID_NOT_FOUND, "User not Found");
        }
        User user = UserUtils.toDto(optionalBase.get());
        userCache.put(user);
        log.info("Fetched from DB");
        return user;
    }

    @Override
    public User createUser(UserCreateRequest request) throws Exception {
        Optional<StoredUser> optionalStoredBase = usersDB.save(UserUtils.toDao(request));
        if(!optionalStoredBase.isPresent()){
            throw new UserException(ErrorCode.USER_NOT_SAVED, "User not saved");
        }
        return UserUtils.toDto(optionalStoredBase.get());
    }
}
