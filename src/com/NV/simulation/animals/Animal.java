package com.NV.simulation.animals;

import java.awt.*;

public interface Animal {

    public boolean isDead();

    public void setDead();

    public double getHunger();

    public void setHunger(double hunger);

    public double getReproductionDrive();

    public void setReproductionDrive(double reproductionDrive);

    public double getSurvivalDrive();

    public void setSurvivalDrive(double survivalDrive);

    public double getSpeed();

    public void setSpeed(double speed);

    public double getSensesRange();

    public void setSensesRange(double sensesRange);

    public double getMutationRate();

    public void setMutationRate(double mutationRate);

    public Point getLocation();

    public void setLocation(Point location);

    public double getNutritionExpenses();

    public Point calculateMove();

    public Animal mateWith(Animal other);

}
