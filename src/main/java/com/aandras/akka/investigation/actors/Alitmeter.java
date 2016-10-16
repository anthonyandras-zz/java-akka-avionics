package com.aandras.akka.investigation.actors;

import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.aandras.akka.investigation.actors.events.EventSource;
import com.aandras.akka.investigation.config.SpringExtension;
import com.aandras.akka.investigation.messages.AltimeterUpdate;
import com.aandras.akka.investigation.messages.RateChange;
import com.aandras.akka.investigation.messages.Tick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by anthonyandras on 10/15/16.
 */
@Component
@Scope("prototype")
public class Alitmeter extends UntypedActor {
    private final int maxRateOfClimb = 5000;
    private float rateOfClimb = 0f;
    private double altitude = 0f;
    private long lastTick = System.currentTimeMillis();
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final Cancellable ticker = getContext().system().scheduler().schedule(Duration.create(100, TimeUnit.MILLISECONDS),
            Duration.create(100, TimeUnit.MILLISECONDS), self(), new Tick(), getContext().system().dispatcher(), null);

    @Autowired
    private EventSource events;

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof RateChange) {
            final RateChange rateChange = (RateChange) message;
            rateOfClimb = rateChange.getAmount() * maxRateOfClimb;
            log.info("Altimeter change rate of clib to " + rateOfClimb);
        } else if (message instanceof Tick) {
            final long tick = System.currentTimeMillis();
            altitude = altitude + (( tick - lastTick / 60000)) * rateOfClimb;
            lastTick = tick;
            events.sendEvent(new AltimeterUpdate(altitude));
        }
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        ticker.cancel();
    }
}
