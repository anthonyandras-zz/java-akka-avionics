package com.aandras.akka.investigation.config;

import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by anthonyandras on 10/15/16.
 */
@Component
public class SpringExtension implements Extension {
    private ApplicationContext applicationContext;

    public void initialize(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(final String actorBeanName) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
    }

}
