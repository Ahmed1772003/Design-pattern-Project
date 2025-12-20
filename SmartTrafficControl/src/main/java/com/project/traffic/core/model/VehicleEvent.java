package com.project.traffic.core.model;

public class VehicleEvent {
    public final String plate;
    public final double speedKmh;
    public final boolean crossedStopLine;
    public final boolean isEmergency;

    public VehicleEvent(String plate, double speedKmh, boolean crossedStopLine, boolean isEmergency) {
        this.plate = plate;
        this.speedKmh = speedKmh;
        this.crossedStopLine = crossedStopLine;
        this.isEmergency = isEmergency;
    }
}
