package com.project.traffic.factory;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.chain.Handler;

public interface IntersectionFactory {
    TrafficLight createTrafficLight();
    Handler createViolationChain();
    int getSpeedLimitKmh();
    String getName();
}
