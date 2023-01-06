package com.thrive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Configuration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserServiceConfiguration extends Configuration {
    private ShardedHibernateFactory shards;
}
