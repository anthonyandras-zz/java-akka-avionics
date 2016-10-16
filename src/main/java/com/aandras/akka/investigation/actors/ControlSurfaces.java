package com.aandras.akka.investigation.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import com.aandras.akka.investigation.config.SpringExtension;
import com.aandras.akka.investigation.messages.RateChange;
import com.aandras.akka.investigation.messages.StickBack;
import com.aandras.akka.investigation.messages.StickForward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by anthonyandras on 10/15/16.
 */

@Component
@Scope("prototype")
public class ControlSurfaces extends UntypedActor {
    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    private ActorRef alitmeter;

    @PostConstruct
    private void init() {
        alitmeter = actorSystem.actorOf(springExtension.props("alitmeter"), "altimeter");
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof StickBack) {
            final StickBack stickBack = (StickBack) message;
            alitmeter.tell(new RateChange(stickBack.getAmount()), self());
        } else if (message instanceof StickForward) {
            final StickForward stickForward = (StickForward) message;
            alitmeter.tell(new RateChange(-1 * stickForward.getAmount()), self());
        }
    }
}
