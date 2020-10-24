package com.NV.simulation.animals;

import java.awt.*;

public class AnimalRabbit extends AnimalHerbivore {


    public AnimalRabbit() {
        super();
    }

    public AnimalRabbit(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
    }
}
