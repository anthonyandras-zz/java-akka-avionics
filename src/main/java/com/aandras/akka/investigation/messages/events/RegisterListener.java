package com.aandras.akka.investigation.messages.events;

import akka.actor.ActorRef;

/**
 * Created by anthonyandras on 10/15/16.
 */
public class RegisterListener {
    private final ActorRef actor;

    public RegisterListener(final ActorRef actor) {
        this.actor = actor;
    }

    public ActorRef getActor() {
        return actor;
    }
}
