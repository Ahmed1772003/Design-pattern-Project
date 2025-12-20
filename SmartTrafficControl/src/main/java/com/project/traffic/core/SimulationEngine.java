package com.project.traffic.core;

import com.project.traffic.bridge.TrafficLight;
import com.project.traffic.chain.Context;
import com.project.traffic.chain.Handler;
import com.project.traffic.core.model.VehicleEvent;
import com.project.traffic.core.scenario.TrafficScenario;

import java.util.Random;

public class SimulationEngine {
    private final TrafficLight light;
    private final Handler chain;
    private final Random rnd = new Random();

    private TrafficScenario scenario = TrafficScenario.normal();

    public SimulationEngine(TrafficLight light, Handler chain) {
        this.light = light;
        this.chain = chain;
    }

    public TrafficLight getLight() { return light; }
    public TrafficScenario getScenario() { return scenario; }
    public int getTrafficLoad() { return scenario.trafficLoad; }

    public void applyScenario(TrafficScenario scenario) {
        if (scenario != null) this.scenario = scenario;
    }

    public void tick() {
        light.step(getTrafficLoad());
        VehicleEvent e = generateRandomEvent();
        processVehicleEvent(e);
    }

    public void processVehicleEvent(VehicleEvent e) {
        Context ctx = new Context();
        ctx.emergencyPriorityEnabled = scenario.emergencyPriority;
        chain.handle(e, light, ctx);
    }

    private VehicleEvent generateRandomEvent() {
        double speed = 20 + rnd.nextInt(120);
        boolean crossed = scenario.accidentMode ? (rnd.nextInt(100) < 65) : rnd.nextBoolean();
        boolean emergency = scenario.emergencyPriority && (rnd.nextInt(12) == 0);
        return new VehicleEvent(
                "EG-" + (1000 + rnd.nextInt(9000)),
                speed,
                crossed,
                emergency
        );
    }
}
