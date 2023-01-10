package com.thrive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.thrive.client.cache.user.Impl.UserCacheImpl;
import com.thrive.client.cache.user.UserCache;
import com.thrive.db.UserFileS3;
import com.thrive.db.UsersDB;
import com.thrive.db.impl.UserFileS3Impl;
import com.thrive.db.impl.UsersDBImpl;
import com.thrive.model.config.CacheConfig;
import com.thrive.model.config.AWSCredential;
import com.thrive.model.dao.StoredUser;
import com.thrive.services.UserService;
import com.thrive.services.impl.UserServiceImpl;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import io.dropwizard.setup.Environment;
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
        bind(UserFileS3.class).to(UserFileS3Impl.class);
    }

    @Provides
    @Singleton
    public RelationalDao<StoredUser> baseRelationalDao() {
        return dbShardingBundle.createRelatedObjectDao(StoredUser.class);
    }

    @Provides
    @Singleton
    public Config provideRedisConfiguration(UserServiceConfiguration userServiceConfiguration) {
        return userServiceConfiguration.getRedis();
    }

    @Provides
    @Singleton
    public CacheConfig provideCacheConfiguration(UserServiceConfiguration userServiceConfiguration) {
        return userServiceConfiguration.getCaches();
    }

    @Provides
    @Singleton
    public AWSCredential provideAWSCredential(UserServiceConfiguration userServiceConfiguration) {
        return userServiceConfiguration.getAWSCredential();
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper(Environment environment) {
        return environment.getObjectMapper();
    }
}
