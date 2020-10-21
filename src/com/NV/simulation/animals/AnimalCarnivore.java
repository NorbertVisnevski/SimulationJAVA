package com.NV.simulation.animals;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

public abstract class AnimalCarnivore extends AnimalBase {


    public AnimalCarnivore() {
        super();
    }

    public AnimalCarnivore(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super( hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
    }

    @Override
    public void setDead()
    {
        super.setDead();
    }

    @Override
    protected void findFood(List<Point> possibleMoves)
    {
        java.util.List<Animal> herbivores = MasterData.animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        herbivores = herbivores.stream().filter(animal->animal instanceof AnimalHerbivore).collect(Collectors.toList());


        if(herbivores.size()==0)
        {
            possibleMoves.add(tileOptions.get(MasterData.random.nextInt(tileOptions.size())).getPosition());
            return;
        }

        List<Point> pathTo = new ArrayList<>();
        for(Animal anim : herbivores)
        {
            pathTo.add(Map.findClosest(tileOptions, anim.getLocation()));
        }
        int x = 0;
        int y = 0;

        for (Point p : pathTo)
        {
            x+=p.x;
            y+=p.y;
        }

        possibleMoves.add( new Point(x/pathTo.size(), y/pathTo.size()));

    }

    @Override
    protected void survive(List<Point> possibleMoves)
    {
        findFood(possibleMoves);
    }
}
