package com.simulation.animals;

import com.simulation.MasterData;
import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.List;

public abstract class Carnivore extends Animal {

    public Carnivore(Map map, AnimalManager animalManager) {
        super(map, animalManager);
    }

    public Carnivore(Map map, AnimalManager animalManager, double hunger, double reproductionDrive, double survivalDrive, String sex, double speed, double sensesRange, double mutationRate, Point location) {
        super(map, animalManager, hunger, reproductionDrive, survivalDrive, sex, speed, sensesRange, mutationRate, location);
    }

    @Override
    protected void findFood(List<Point> possibleMoves)
    {
        java.util.List<Animal> herbivores = animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        herbivores = herbivores.stream().filter(animal->animal instanceof Herbivore).collect(Collectors.toList());


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
