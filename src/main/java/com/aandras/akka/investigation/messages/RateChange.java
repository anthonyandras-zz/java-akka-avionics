package com.aandras.akka.investigation.messages;

/**
 * Created by anthonyandras on 10/15/16.
 */
public class RateChange {
    private float amount;

    public RateChange(final float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }
}
