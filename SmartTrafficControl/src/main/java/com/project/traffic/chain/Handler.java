package com.project.traffic.chain;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.core.model.VehicleEvent;

public abstract class Handler {
    private Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public final void handle(VehicleEvent event, TrafficLight light, Context ctx) {
        if (process(event, light, ctx) && next != null) {
            next.handle(event, light, ctx);
        }
    }

    protected abstract boolean process(VehicleEvent event, TrafficLight light, Context ctx);
}
