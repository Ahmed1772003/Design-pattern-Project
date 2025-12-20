package com.project.traffic.core.scenario;

/**
 * Bonus: Builder Pattern
 * A scenario is a configurable set of parameters that affects simulation behavior.
 */
public class TrafficScenario {
    public final String name;
    public final int trafficLoad;
    public final boolean accidentMode;
    public final boolean emergencyPriority;

    private TrafficScenario(Builder b) {
        this.name = b.name;
        this.trafficLoad = b.trafficLoad;
        this.accidentMode = b.accidentMode;
        this.emergencyPriority = b.emergencyPriority;
    }

    public static class Builder {
        private String name = "Normal";
        private int trafficLoad = 5;
        private boolean accidentMode = false;
        private boolean emergencyPriority = false;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder trafficLoad(int trafficLoad) {
            this.trafficLoad = trafficLoad;
            return this;
        }

        public Builder accidentMode(boolean accidentMode) {
            this.accidentMode = accidentMode;
            return this;
        }

        public Builder emergencyPriority(boolean emergencyPriority) {
            this.emergencyPriority = emergencyPriority;
            return this;
        }

        public TrafficScenario build() {
            return new TrafficScenario(this);
        }
    }

    // Convenience presets (still built via Builder)
    public static TrafficScenario normal() {
        return new Builder()
                .name("Normal")
                .trafficLoad(5)
                .accidentMode(false)
                .emergencyPriority(false)
                .build();
    }

    public static TrafficScenario rushHour() {
        return new Builder()
                .name("Rush Hour")
                .trafficLoad(15)
                .accidentMode(false)
                .emergencyPriority(true)
                .build();
    }

    public static TrafficScenario accident() {
        return new Builder()
                .name("Accident Mode")
                .trafficLoad(18)
                .accidentMode(true)
                .emergencyPriority(true)
                .build();
    }

    public static TrafficScenario emergency() {
        return new Builder()
                .name("Emergency Mode")
                .trafficLoad(10)
                .accidentMode(false)
                .emergencyPriority(true)
                .build();
    }
}
