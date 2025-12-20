package com.project.traffic.core;

import com.project.traffic.core.model.Violation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TrafficControlCenter {
    private static final TrafficControlCenter INSTANCE = new TrafficControlCenter();
    private final List<Violation> violations = new ArrayList<>();

    private TrafficControlCenter() {}

    public static TrafficControlCenter getInstance() {
        return INSTANCE;
    }

    public void logViolation(Violation v) {
        violations.add(v);
    }

    public List<Violation> getViolationsSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(violations));
    }

    public void clearViolations() {
        violations.clear();
    }
}
