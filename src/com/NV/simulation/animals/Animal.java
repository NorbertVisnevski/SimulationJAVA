package com.NV.simulation.animals;

import java.awt.*;

public interface Animal {

    boolean isDead();

    void setDead();

    double getHunger();

    void setHunger(double hunger);

    double getReproductionDrive();

    void setReproductionDrive(double reproductionDrive);

    double getSurvivalDrive();

    void setSurvivalDrive(double survivalDrive);

    double getSpeed();

    void setSpeed(double speed);

    double getSensesRange();

    void setSensesRange(double sensesRange);

    double getMutationRate();

    void setMutationRate(double mutationRate);

    Point getLocation();

    void setLocation(Point location);

    boolean canProcreateWith(Animal animal);

    double getNutritionExpenses();

    Point calculateMove();

    Animal mateWith(Animal other) throws Exception;

}
