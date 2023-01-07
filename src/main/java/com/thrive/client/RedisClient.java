package com.thrive.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Objects;

@Slf4j
@Singleton
public class RedisClient implements Managed {
    private Config redisConfiguration;
    private RedissonClient redisClient;

    @Inject
    public RedisClient(Config redisConfiguration) {
        this.redisConfiguration = redisConfiguration;
    }

    private RedissonClient getRedissonClient() {
        redisClient = Redisson.create(redisConfiguration);
        return redisClient;
    }

    public RedissonClient getClient() {
        if(Objects.isNull(redisClient)) {
            redisClient = getRedissonClient();
        }
        return redisClient;
    }


    @Override
    public void start() throws Exception {
        redisClient = getRedissonClient();
    }

    @Override
    public void stop() throws Exception {
        if(Objects.nonNull(redisClient))
            redisClient.shutdown();
    }
}
