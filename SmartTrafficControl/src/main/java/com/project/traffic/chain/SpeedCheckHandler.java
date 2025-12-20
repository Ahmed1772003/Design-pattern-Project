package com.project.traffic.chain;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.core.model.VehicleEvent;

public class SpeedCheckHandler extends Handler {
    private final int speedLimit;

    public SpeedCheckHandler(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    @Override
    protected boolean process(VehicleEvent event, TrafficLight light, Context ctx) {
        ctx.speedViolation = event.speedKmh > speedLimit;
        return true;
    }
}
