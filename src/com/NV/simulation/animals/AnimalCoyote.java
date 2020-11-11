package com.NV.simulation.animals;

import java.awt.*;

public class AnimalCoyote extends AnimalCarnivore{

    public AnimalCoyote() {
        super();
    }

    public AnimalCoyote(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
    }

    @Override
    protected void initHuntedAnimals() {
        huntedAnimals.add(AnimalRabbit.class);
        huntedAnimals.add(AnimalFox.class);
    }

    @Override
    public boolean canProcreateWith(Animal animal) {
        return (super.canProcreateWith(animal) || animal.getClass().equals(AnimalWolf.class));
    }
}
