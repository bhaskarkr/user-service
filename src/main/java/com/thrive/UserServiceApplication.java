package com.thrive;

import com.google.inject.Stage;
import com.thrive.model.dao.StoredStock;
import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredWallet;
import com.thrive.resources.UserResource;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.val;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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
        this.dbShardingBundle = new DBShardingBundle<UserServiceConfiguration>(
                StoredUser.class,
                StoredStock.class,
                StoredWallet.class) {
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
        environment.jersey().register(injector.getInstance(UserResource.class));
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}
