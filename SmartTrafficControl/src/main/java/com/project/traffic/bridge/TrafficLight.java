package com.project.traffic.bridge;

import com.project.traffic.core.model.TrafficSignalState;

public abstract class TrafficLight {
    protected final ControlAlgorithm algorithm;
    protected TrafficSignalState state = TrafficSignalState.RED;
    protected int tick = 0;

    protected TrafficLight(ControlAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public TrafficSignalState getState() { return state; }
    public int getTick() { return tick; }

    public void step(int trafficLoad) {
        tick++;
        state = algorithm.nextState(state, tick, trafficLoad);
    }
}
