package com.NV.simulation.animals;

import java.awt.*;

public class AnimalRabbit extends AnimalHerbivore {

    private static long rabbitCount = 0;

    public AnimalRabbit() {
        super();
        ++rabbitCount;
    }

    public AnimalRabbit(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
        ++rabbitCount;
    }

    public static long getCount()
    {
        return rabbitCount;
    }

    @Override
    public void setDead()
    {
        super.setDead();
        --rabbitCount;
    }

}
