package com.aandras.akka.investigation.messages;

/**
 * Created by anthonyandras on 10/15/16.
 */
public class StickForward {
    private final float amount;

    public StickForward(final float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }
}
