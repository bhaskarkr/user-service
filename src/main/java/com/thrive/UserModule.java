package com.thrive;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.thrive.db.UsersDB;
import com.thrive.db.impl.UsersDBImpl;
import com.thrive.model.dao.StoredUser;
import com.thrive.services.UserService;
import com.thrive.services.impl.UserServiceImpl;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.dao.RelationalDao;


public class UserModule extends AbstractModule {

    DBShardingBundle<UserServiceConfiguration> dbShardingBundle;

    public UserModule(DBShardingBundle<UserServiceConfiguration> dbShardingBundle) {
        this.dbShardingBundle = dbShardingBundle;
    }

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UsersDB.class).to(UsersDBImpl.class);
    }

    @Provides
    @Singleton
    public RelationalDao<StoredUser> baseRelationalDao() {
        return dbShardingBundle.createRelatedObjectDao(StoredUser.class);
    }
}
