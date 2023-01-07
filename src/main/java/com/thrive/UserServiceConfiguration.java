package com.thrive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thrive.client.RedisConfiguration;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Configuration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.redisson.config.Config;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserServiceConfiguration extends Configuration {
    private ShardedHibernateFactory shards;
    private Config redis;
}
