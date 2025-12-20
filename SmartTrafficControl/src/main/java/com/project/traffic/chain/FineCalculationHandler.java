package com.project.traffic.chain;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.core.model.VehicleEvent;

public class FineCalculationHandler extends Handler {
    @Override
    protected boolean process(VehicleEvent event, TrafficLight light, Context ctx) {
        if (ctx.ignoreBecauseEmergency) return true;

        if (ctx.signalViolation) {
            ctx.violationType = "RED_LIGHT";
            ctx.fine += 500;
        }
        if (ctx.speedViolation) {
            ctx.violationType = (ctx.violationType == null) ? "SPEEDING" : (ctx.violationType + "+SPEEDING");
            ctx.fine += 300;
        }
        return true;
    }
}
