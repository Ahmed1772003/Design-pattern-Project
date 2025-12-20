package com.project.traffic.bridge;

import com.project.traffic.core.model.TrafficSignalState;

/**
 * A tiny "adaptive" algorithm:
 * - If trafficLoad is high, keep GREEN longer (change every 7 ticks)
 * - Otherwise use normal 5-tick cycle.
 */
public class AdaptiveTrafficAlgorithm implements ControlAlgorithm {
    @Override
    public TrafficSignalState nextState(TrafficSignalState current, int tick, int trafficLoad) {
        int period = (trafficLoad >= 12) ? 7 : 5;
        if (tick % period != 0) return current;

        return switch (current) {
            case RED -> TrafficSignalState.GREEN;
            case GREEN -> TrafficSignalState.YELLOW;
            case YELLOW -> TrafficSignalState.RED;
        };
    }
}
