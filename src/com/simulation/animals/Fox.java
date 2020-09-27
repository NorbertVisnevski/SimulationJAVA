package com.simulation.animals;

import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;
import com.simulation.managers.ReproductionHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Fox extends Carnivore {

    public Fox(Map map, AnimalManager animalManager) {
        super(map, animalManager);
    }

    public Fox(Map map, AnimalManager animalManager, double hunger, double reproductionDrive, double survivalDrive, String sex, double speed, double sensesRange, double mutationRate, Point location) {
        super(map, animalManager, hunger, reproductionDrive, survivalDrive, sex, speed, sensesRange, mutationRate, location);
    }

    @Override
    protected void survive(java.util.List<Point> possibleMoves)
    {
        java.util.List<Animal> carnivores = animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        carnivores = carnivores.stream().filter(animal->animal instanceof Wolf).collect(Collectors.toList());

        if(carnivores.size()==0)
        {
            Random rand = new Random();
            possibleMoves.add(tileOptions.get(rand.nextInt(tileOptions.size())).getPosition());
            return;
        }

        List<Point> pathOptions = new ArrayList<>();
        for(Animal anim : carnivores)
        {
            pathOptions.add(Map.findFurthest(tileOptions, anim.getLocation()));
        }
        int x = 0;
        int y = 0;

        for (Point p : pathOptions)
        {
            x+=p.x;
            y+=p.y;
        }

        possibleMoves.add( new Point(x/pathOptions.size(), y/pathOptions.size()));
    }

    @Override
    public Fox mateWith(Animal other)
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

            Fox anim =  new Fox(map,animalManager);
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
