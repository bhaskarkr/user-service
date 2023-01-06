package com.thrive;

import com.thrive.resources.userResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class simpleApplication extends Application<simpleConfiguration> {

    public static void main(final String[] args) throws Exception {
        new simpleApplication().run(args);
    }

    @Override
    public String getName() {
        return "simple";
    }

    @Override
    public void initialize(final Bootstrap<simpleConfiguration> bootstrap) {

    }

    @Override
    public void run(final simpleConfiguration configuration,
                    final Environment environment) {
        userResource resource = new userResource();
        environment.jersey().register(resource);
    }

}
