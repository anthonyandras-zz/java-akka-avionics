package com.aandras.akka.investigation.messages.events;

import akka.actor.ActorRef;

/**
 * Created by anthonyandras on 10/15/16.
 */
public class UnregisterListener {
    private final ActorRef listener;

    public UnregisterListener(final ActorRef listener) {
        this.listener = listener;
    }

    public ActorRef getListener() {
        return listener;
    }
}
