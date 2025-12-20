package com.project.traffic.chain;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.core.model.VehicleEvent;

public class EmergencyVehicleHandler extends Handler {
    @Override
    protected boolean process(VehicleEvent event, TrafficLight light, Context ctx) {
        if (ctx.emergencyPriorityEnabled && event.isEmergency) {
            ctx.ignoreBecauseEmergency = true;
        }
        return true;
    }
}
