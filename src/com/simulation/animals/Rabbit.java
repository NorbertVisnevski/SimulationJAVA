package com.simulation.animals;

import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;
import com.simulation.managers.ReproductionHelper;

import java.awt.*;

public class Rabbit extends Herbivore {

    public Rabbit(Map map, AnimalManager animalManager) {
        super(map, animalManager);
    }

    public Rabbit(Map map, AnimalManager animalManager, double hunger, double reproductionDrive, double survivalDrive, String sex, double speed, double sensesRange, double mutationRate, Point location) {
        super(map, animalManager, hunger, reproductionDrive, survivalDrive, sex, speed, sensesRange, mutationRate, location);
    }

    @Override
    public Rabbit mateWith(Animal other)
    {
        if(other.getClass() != this.getClass())
        {
            return null;
        }
        else
        {
            double mr1 = this.getMutationRate();
            double mr2 = other.getMutationRate();
            double reproductionDrive = ReproductionHelper.getProperty(this.getReproductionDrive(), other.getReproductionDrive(), mr1, mr2);
            double speed = ReproductionHelper.getProperty(this.getSpeed(), other.getSpeed(), mr1, mr2);
            double survivalDrive = ReproductionHelper.getProperty(this.getSurvivalDrive(), other.getSurvivalDrive(), mr1, mr2);
            double sensesRadius = ReproductionHelper.getProperty(this.getSensesRange(), other.getSensesRange(), mr1, mr2);
            double mutationRate = ReproductionHelper.getProperty(this.getMutationRate(), other.getMutationRate(), mr1, mr2);

            Rabbit anim =  new Rabbit(map, animalManager);
            anim.setLocation(this.getLocation());
            anim.setSpeed(speed);
            anim.setHunger(anim.getNutritionExpenses()*2.0);
            anim.setReproductionDrive(reproductionDrive);
            anim.setMutationRate(mutationRate);
            anim.setSurvivalDrive(survivalDrive);
            anim.setSensesRange(sensesRadius);
            anim.setSex(ReproductionHelper.getSex());

            this.setHunger(this.getHunger()+25);
            other.setHunger(other.getHunger()+25);

            return anim;
        }
    }
}
