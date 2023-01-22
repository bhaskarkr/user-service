package com.thrive;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.thrive.client.cache.user.Impl.UserCacheImpl;
import com.thrive.client.cache.user.UserCache;
import com.thrive.db.StockDB;
import com.thrive.db.UserStockMappingDB;
import com.thrive.db.UsersDB;
import com.thrive.db.WalletDB;
import com.thrive.db.impl.StockDBImpl;
import com.thrive.db.impl.UserStockMappingDBImpl;
import com.thrive.db.impl.UsersDBImpl;
import com.thrive.db.impl.WalletDBImpl;
import com.thrive.model.config.CacheConfig;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredUserStockMapping;
import com.thrive.model.dao.StoredWallet;
import com.thrive.services.*;
import com.thrive.services.impl.*;
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
        bind(WalletDB.class).to(WalletDBImpl.class);
        bind(WalletService.class).to(WalletServiceImpl.class);
        bind(UserStockMappingDB.class).to(UserStockMappingDBImpl.class);
        bind(UserStockMappingService.class).to(UserStockMappingServiceImpl.class);
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
    public RelationalDao<StoredWallet> walletRelationalDao() {
        return dbShardingBundle.createRelatedObjectDao(StoredWallet.class);
    }

    @Provides
    @Singleton
    public RelationalDao<StoredUserStockMapping> userStockMappingRelationalDao() {
        return dbShardingBundle.createRelatedObjectDao(StoredUserStockMapping.class);
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
