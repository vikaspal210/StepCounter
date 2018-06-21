package com.example.cas.stepcounter;

/**
 * Created by cas on 11/7/2017.
 */

// Will listen to step alerts
public interface StepListener {

    void step(long timeNs);

}
