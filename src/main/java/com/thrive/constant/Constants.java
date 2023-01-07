package com.thrive.constant;

import com.thrive.model.config.CacheNameConfig;

import java.util.concurrent.TimeUnit;

public abstract class Constants {
    public static final CacheNameConfig DEFAULT_CACHE_CONFIG = CacheNameConfig.builder()
            .ttl(100)
            .ttlType(TimeUnit.SECONDS)
            .build();
}
