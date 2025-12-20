package com.project.traffic.bridge;

import com.project.traffic.core.model.TrafficSignalState;

public interface ControlAlgorithm {
    TrafficSignalState nextState(TrafficSignalState current, int tick, int trafficLoad);
}
