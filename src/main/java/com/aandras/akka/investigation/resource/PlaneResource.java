package com.aandras.akka.investigation.resource;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.aandras.akka.investigation.config.SpringExtension;
import com.aandras.akka.investigation.messages.GiveMeControl;
import com.aandras.akka.investigation.messages.StickBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by anthonyandras on 10/16/16.
 */
@RestController
public class PlaneResource {
    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    @RequestMapping("/")
    public String launch() throws Exception{
        final ActorRef plane = actorSystem.actorOf(springExtension.props("plane"), "plane");
        final Timeout timeout = Timeout.durationToTimeout(Duration.create(5, TimeUnit.SECONDS));
        final Future<Object> future = Patterns.ask(plane, new GiveMeControl(), timeout);
        final ActorRef control = (ActorRef) Await.result(future, Duration.create(5, TimeUnit.SECONDS));

        actorSystem.scheduler().scheduleOnce(Duration.create(200, TimeUnit.MILLISECONDS),
                () -> control.tell(new StickBack(1f), null), actorSystem.dispatcher());

        actorSystem.scheduler().scheduleOnce(Duration.create(1, TimeUnit.SECONDS),
                () -> control.tell(new StickBack(0f), null), actorSystem.dispatcher());

        actorSystem.scheduler().scheduleOnce(Duration.create(3, TimeUnit.SECONDS),
                () -> control.tell(new StickBack(0.5f), null), actorSystem.dispatcher());

        actorSystem.scheduler().scheduleOnce(Duration.create(4, TimeUnit.SECONDS),
                () -> control.tell(new StickBack(4f), null), actorSystem.dispatcher());

        actorSystem.scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS),
                () -> actorSystem.terminate(), actorSystem.dispatcher());

        return "launch successful.";
    }
}
