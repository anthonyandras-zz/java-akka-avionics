package com.aandras.akka.investigation.actors.events;

import akka.actor.ActorRef;
import com.aandras.akka.investigation.messages.events.RegisterListener;
import com.aandras.akka.investigation.messages.events.UnregisterListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anthonyandras on 10/15/16.
 */
@Component
public class EventSource {
    private List<ActorRef> listeners;

    @PostConstruct
    public void init() { listeners = new LinkedList<>(); }

    public void sendEvent(final Object event) { listeners.stream().forEach(listener -> listener.tell(event, null));}

    public void addListener(final Object listener) {
        if(listener instanceof RegisterListener) {
            final RegisterListener registerListener = (RegisterListener) listener;
            listeners.add(registerListener.getActor());
        } else if (listener instanceof UnregisterListener) {
            final UnregisterListener unregisterListener = (UnregisterListener) listener;
            listeners = listeners.stream().filter(l -> !l.equals(unregisterListener)).collect(Collectors.toList());
        }
    }
}
