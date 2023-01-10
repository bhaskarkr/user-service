package com.thrive;

import com.google.inject.Stage;
import com.thrive.model.dao.StoredUser;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.val;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class UserServiceApplication extends Application<UserServiceConfiguration> {

    private DBShardingBundle<UserServiceConfiguration> dbShardingBundle;
    private GuiceBundle guiceBundle;

    public static void main(final String[] args) throws Exception {
        new UserServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "UserService";
    }

    @Override
    public void initialize(final Bootstrap<UserServiceConfiguration> bootstrap) {
        this.dbShardingBundle = new DBShardingBundle<UserServiceConfiguration>(StoredUser.class) {
            @Override
            protected ShardedHibernateFactory getConfig(UserServiceConfiguration userServiceConfiguration) {
                return userServiceConfiguration.getShards();
            }
        };
        bootstrap.addBundle(dbShardingBundle);
        this.guiceBundle = GuiceBundle.<UserServiceConfiguration>builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new UserModule(this.dbShardingBundle))
                .build(Stage.PRODUCTION);
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(final UserServiceConfiguration configuration,
                    final Environment environment) {
        val injector = guiceBundle.getInjector();
        environment.jersey().register(MultiPartFeature.class);
    }

}
