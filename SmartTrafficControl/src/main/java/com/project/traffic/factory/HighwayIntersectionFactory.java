package com.project.traffic.factory;

import com.project.traffic.bridge.*;
import com.project.traffic.chain.*;

public class HighwayIntersectionFactory implements IntersectionFactory {
    @Override
    public TrafficLight createTrafficLight() {
        return new BasicTrafficLight(new AdaptiveTrafficAlgorithm());
    }

    @Override
    public Handler createViolationChain() {
        Handler head = new SpeedCheckHandler(getSpeedLimitKmh());
        head.setNext(new SignalViolationHandler())
            .setNext(new EmergencyVehicleHandler())
            .setNext(new FineCalculationHandler())
            .setNext(new LoggingHandler());
        return head;
    }

    @Override
    public int getSpeedLimitKmh() { return 100; }

    @Override
    public String getName() { return "Highway Intersection"; }
}
