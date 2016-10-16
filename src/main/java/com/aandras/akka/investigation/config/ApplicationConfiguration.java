package com.aandras.akka.investigation.config;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by anthonyandras on 10/15/16.
 */
@Configuration
public class ApplicationConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;

    @Bean
    public ActorSystem actorSystem() {
        final ActorSystem system = ActorSystem.create("Altimeter", akkaConfiguration());
        springExtension.initialize(applicationContext);
        return system;
    }

    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
}
