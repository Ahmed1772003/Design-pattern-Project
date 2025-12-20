package com.project.traffic.chain;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.core.model.TrafficSignalState;
import com.project.traffic.core.model.VehicleEvent;

public class SignalViolationHandler extends Handler {
    @Override
    protected boolean process(VehicleEvent event, TrafficLight light, Context ctx) {
        // Example: crossed stop line while RED
        ctx.signalViolation = event.crossedStopLine && light.getState() == TrafficSignalState.RED;
        return true;
    }
}
