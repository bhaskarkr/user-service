package com.thrive.services.impl;

import com.google.inject.Singleton;
import com.thrive.db.UsersDB;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;
import com.thrive.services.UserService;
import com.thrive.util.UserUtils;

import javax.inject.Inject;
import java.util.Optional;

@Singleton
public class UserServiceImpl implements UserService {

    private final UsersDB usersDB;

    @Inject
    public UserServiceImpl(UsersDB usersDB) {
        this.usersDB = usersDB;
    }

    @Override
    public User getUser(String userId, boolean allowInactive) throws Exception {
        Optional<StoredUser> optionalBase = usersDB.get(userId, allowInactive);
//        if(!optionalBase.isPresent()) {
//            throw new BaseException(ErrorCode.BASE_ID_NOT_FOUND, "Base not Found");
//        }
        return UserUtils.toDto(optionalBase.get());
    }

    @Override
    public User createUser(UserCreateRequest request) throws Exception {
        Optional<StoredUser> optionalStoredBase = usersDB.save(UserUtils.toDao(request));
//        if(!optionalStoredBase.isPresent()){
//            throw new BaseException(ErrorCode.BASE_NOT_SAVED, "Base not saved");
//        }

        return UserUtils.toDto(optionalStoredBase.get());
    }
}
