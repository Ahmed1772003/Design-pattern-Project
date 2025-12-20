package com.project.traffic.core.model;

public class Violation {
    public final String plate;
    public final String type;
    public final double fine;
    public final long timestamp;

    public Violation(String plate, String type, double fine, long timestamp) {
        this.plate = plate;
        this.type = type;
        this.fine = fine;
        this.timestamp = timestamp;
    }
}
