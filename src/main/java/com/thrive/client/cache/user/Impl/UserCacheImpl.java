package com.thrive.client.cache.user.Impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.client.cache.RedisClient;
import com.thrive.client.cache.CacheName;
import com.thrive.client.cache.user.UserCache;
import com.thrive.constant.Constants;
import com.thrive.model.config.CacheConfig;
import com.thrive.model.config.CacheNameConfig;
import com.thrive.model.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;

import java.util.Objects;
import java.util.Optional;

@Singleton
@Slf4j
public class UserCacheImpl implements UserCache {
    private final CacheNameConfig cacheNameConfig;
    private final RMapCache<String, User> userRMapCache;
    @Inject
    public UserCacheImpl(RedisClient redisClient, CacheConfig cacheConfig) {
        this.cacheNameConfig = cacheConfig.getMapConfig().getOrDefault(CacheName.USER, Constants.DEFAULT_CACHE_CONFIG);
        this.userRMapCache = redisClient.getClient().getMapCache(CacheName.USER.name());
    }

    @Override
    public Optional<User> get(String userId) {
        User user = userRMapCache.getOrDefault(userId, null);
        if(Objects.nonNull(user)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public void put(User user) {
        userRMapCache.put(user.getId(), user, cacheNameConfig.getTtl(), cacheNameConfig.getTtlType());
    }
}
