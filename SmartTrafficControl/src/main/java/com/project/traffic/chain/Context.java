package com.project.traffic.chain;

public class Context {
    public boolean speedViolation = false;
    public boolean signalViolation = false;

    // if true -> ignore violations for emergency vehicles
    public boolean ignoreBecauseEmergency = false;

    // Scenario option: if false, emergency vehicles are treated as normal vehicles
    public boolean emergencyPriorityEnabled = true;

    public String violationType = null;
    public double fine = 0.0;
}
