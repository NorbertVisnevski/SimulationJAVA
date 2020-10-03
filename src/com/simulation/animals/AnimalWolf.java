package com.simulation.animals;

import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;
import com.simulation.managers.ReproductionHelper;

import java.awt.*;

public class AnimalWolf extends AnimalCarnivore {

    private static long wolfCount = 0;

    public AnimalWolf() {
        super();
        ++wolfCount;
    }

    public AnimalWolf(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
        ++wolfCount;
    }

    public static long getCount()
    {
        return wolfCount;
    }

    @Override
    public void setDead()
    {
        super.setDead();
        --wolfCount;
    }

}
