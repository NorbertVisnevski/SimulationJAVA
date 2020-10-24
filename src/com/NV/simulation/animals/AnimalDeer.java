package com.NV.simulation.animals;

import java.awt.*;

public class AnimalDeer extends AnimalHerbivore {
    public AnimalDeer() {
        super();
    }

    public AnimalDeer(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
    }
}
