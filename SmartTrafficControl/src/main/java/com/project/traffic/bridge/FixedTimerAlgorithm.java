package com.project.traffic.bridge;

import com.project.traffic.core.model.TrafficSignalState;

public class FixedTimerAlgorithm implements ControlAlgorithm {
    @Override
    public TrafficSignalState nextState(TrafficSignalState current, int tick, int trafficLoad) {
        // Simple cycle: every 5 ticks change state
        if (tick % 5 != 0) return current;
        return switch (current) {
            case RED -> TrafficSignalState.GREEN;
            case GREEN -> TrafficSignalState.YELLOW;
            case YELLOW -> TrafficSignalState.RED;
        };
    }
}
