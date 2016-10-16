package com.aandras.akka.investigation.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.aandras.akka.investigation.actors.events.EventSource;
import com.aandras.akka.investigation.config.SpringExtension;
import com.aandras.akka.investigation.messages.AltimeterUpdate;
import com.aandras.akka.investigation.messages.GiveMeControl;
import com.aandras.akka.investigation.messages.events.RegisterListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by anthonyandras on 10/15/16.
 */
@Component
@Scope("prototype")
public class Plane extends UntypedActor {
   @Autowired
   private ActorSystem actorSystem;

   @Autowired
   private EventSource eventSource;

   @Autowired
   private SpringExtension springExtension;

   private ActorRef controls;
   private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

   @PostConstruct
   public void init() {
      controls = actorSystem.actorOf(springExtension.props("controlSurfaces"), "ControlSurfaces");
   }

   @Override
   public void preStart() throws Exception {
      super.preStart();
      eventSource.addListener(new RegisterListener(self()));
   }

   @Override
   public void onReceive(Object message) throws Throwable {
      if(message instanceof GiveMeControl) {
         log.info("Plane givng controls...");
         getSender().tell(controls, self());
      } else if (message instanceof AltimeterUpdate) {
         final AltimeterUpdate update = (AltimeterUpdate) message;
         log.info("Altitude is now: " + update.getAltitude());
      }
   }
}
