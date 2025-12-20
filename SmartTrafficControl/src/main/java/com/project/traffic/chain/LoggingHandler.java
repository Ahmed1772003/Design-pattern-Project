package com.project.traffic.chain;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.core.TrafficControlCenter;
import com.project.traffic.core.model.VehicleEvent;
import com.project.traffic.core.model.Violation;

public class LoggingHandler extends Handler {
    @Override
    protected boolean process(VehicleEvent event, TrafficLight light, Context ctx) {
        if (ctx.ignoreBecauseEmergency) return true;
        if (ctx.fine <= 0) return true;

        TrafficControlCenter.getInstance().logViolation(
            new Violation(event.plate, ctx.violationType, ctx.fine, System.currentTimeMillis())
        );
        return true;
    }
}
