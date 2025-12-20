package com.project.traffic.factory;

import com.project.traffic.bridge.*;
import com.project.traffic.chain.*;

public class NormalIntersectionFactory implements IntersectionFactory {
    @Override
    public TrafficLight createTrafficLight() {
        return new BasicTrafficLight(new FixedTimerAlgorithm());
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
    public int getSpeedLimitKmh() { return 60; }

    @Override
    public String getName() { return "Normal Intersection"; }
}
