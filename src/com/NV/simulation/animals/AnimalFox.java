package com.NV.simulation.animals;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalFox extends AnimalCarnivore {

    private static long foxCount = 0;

    public AnimalFox() {
        super();
        ++foxCount;
    }

    public AnimalFox(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
        ++foxCount;
    }

    public static long getCount()
    {
        return foxCount;
    }

    @Override
    public void setDead()
    {
        super.setDead();
        --foxCount;
    }

    @Override
    protected void survive(java.util.List<Point> possibleMoves)
    {
        java.util.List<Animal> carnivores = MasterData.animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        carnivores = carnivores.stream().filter(animal->animal instanceof AnimalWolf).collect(Collectors.toList());

        if(carnivores.size()==0)
        {
            possibleMoves.add(tileOptions.get(MasterData.random.nextInt(tileOptions.size())).getPosition());
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

}