package com.thrive;

import com.thrive.model.dto.StoredBase;
import com.thrive.resources.userResource;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class UserServiceApplication extends Application<UserServiceConfiguration> {

    private DBShardingBundle<UserServiceConfiguration> dbShardingBundle;

    public static void main(final String[] args) throws Exception {
        new UserServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "UserService";
    }

    @Override
    public void initialize(final Bootstrap<UserServiceConfiguration> bootstrap) {
        this.dbShardingBundle = new DBShardingBundle<UserServiceConfiguration>(StoredBase.class) {
            @Override
            protected ShardedHibernateFactory getConfig(UserServiceConfiguration baseProjectConfiguration) {
                return baseProjectConfiguration.getShards();
            }
        };
        bootstrap.addBundle(dbShardingBundle);
    }

    @Override
    public void run(final UserServiceConfiguration configuration,
                    final Environment environment) {
        userResource resource = new userResource();
        environment.jersey().register(resource);
    }

}
