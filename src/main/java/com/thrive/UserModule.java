package com.thrive;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.thrive.client.cache.user.Impl.UserCacheImpl;
import com.thrive.client.cache.user.UserCache;
import com.thrive.db.StockDB;
import com.thrive.db.UsersDB;
import com.thrive.db.impl.StockDBImpl;
import com.thrive.db.impl.UsersDBImpl;
import com.thrive.model.config.CacheConfig;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import com.thrive.services.SessionService;
import com.thrive.services.StockService;
import com.thrive.services.UserService;
import com.thrive.services.impl.SessionServiceImpl;
import com.thrive.services.impl.StockServiceImpl;
import com.thrive.services.impl.UserServiceImpl;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.redisson.config.Config;


public class UserModule extends AbstractModule {

    DBShardingBundle<UserServiceConfiguration> dbShardingBundle;

    public UserModule(DBShardingBundle<UserServiceConfiguration> dbShardingBundle) {
        this.dbShardingBundle = dbShardingBundle;
    }

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UsersDB.class).to(UsersDBImpl.class);
        bind(UserCache.class).to(UserCacheImpl.class);
        bind(SessionService.class).to(SessionServiceImpl.class);
        bind(StockService.class).to(StockServiceImpl.class);
        bind(StockDB.class).to(StockDBImpl.class);
    }

    @Provides
    @Singleton
    public RelationalDao<StoredUser> userRelationalDao() {
        return dbShardingBundle.createRelatedObjectDao(StoredUser.class);
    }

    @Provides
    @Singleton
    public RelationalDao<StoredStock> stockRelationalDao() {
        return dbShardingBundle.createRelatedObjectDao(StoredStock.class);
    }

    @Provides
    @Singleton
    public Config getRedisConfiguration(UserServiceConfiguration userServiceConfiguration) {
        return userServiceConfiguration.getRedis();
    }

    @Provides
    @Singleton
    public CacheConfig getCacheConfiguration(UserServiceConfiguration userServiceConfiguration) {
        return userServiceConfiguration.getCaches();
    }
}
