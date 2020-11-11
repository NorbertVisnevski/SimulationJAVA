package com.NV.simulation.animals;

import com.NV.simulation.MasterData;
import com.NV.simulation.managers.map.Map;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AnimalCarnivore extends AnimalBase {

    protected final Set<Class<?>> huntedAnimals = new HashSet<>();

    public AnimalCarnivore() {
        super();
        initHuntedAnimals();
    }

    public AnimalCarnivore(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super( hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
        initHuntedAnimals();
    }

    @Override
    protected void findFood(List<Point> possibleMoves)
    {
        java.util.List<Animal> herbivores = MasterData.animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        herbivores = herbivores.stream().filter(this::canConsume).collect(Collectors.toList());


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

    public boolean canConsume(Animal animal)
    {
        return huntedAnimals.contains(animal.getClass());
    }

    protected abstract void initHuntedAnimals();

    @Override
    protected void survive(List<Point> possibleMoves)
    {
        findFood(possibleMoves);
    }
}
