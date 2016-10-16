package com.aandras.akka.investigation.messages;

/**
 * Created by anthonyandras on 10/15/16.
 */
public class AltimeterUpdate {
    private final double altitude;

    public AltimeterUpdate(final double altitude) {
        this.altitude = altitude;
    }

    public double getAltitude() {
        return altitude;
    }
}
