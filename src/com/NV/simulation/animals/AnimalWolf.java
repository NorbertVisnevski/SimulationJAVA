package com.NV.simulation.animals;

import java.awt.*;

public class AnimalWolf extends AnimalCarnivore {

    public AnimalWolf() {
        super();
    }

    public AnimalWolf(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
    }

}
