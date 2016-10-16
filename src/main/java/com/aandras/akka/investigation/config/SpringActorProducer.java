package com.aandras.akka.investigation.config;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * Created by anthonyandras on 10/15/16.
 */
public class SpringActorProducer implements IndirectActorProducer{
    private final ApplicationContext applicationContext;
    private final String actorBeanName;

    public SpringActorProducer(final ApplicationContext applicationContext, final String actorBeanName) {
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
    }

    @Override
    public Actor produce() {
        return (Actor) applicationContext.getBean(actorBeanName);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
    }
}
