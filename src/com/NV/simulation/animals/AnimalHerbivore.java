package com.NV.simulation.animals;

import com.NV.simulation.MasterData;
import com.NV.simulation.managers.map.Map;
import com.NV.simulation.tile.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AnimalHerbivore extends AnimalBase {

    public AnimalHerbivore() {
        super();
    }

    public AnimalHerbivore(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        super(hunger, reproductionDrive, survivalDrive, speed, sensesRange, mutationRate, location);
    }

    @Override
    protected void findFood(List<Point> possibleMoves)
    {
        List<Tile> list = new ArrayList<>(tileOptions);
        Collections.shuffle(list);

        Tile currentTile = list.stream().filter(tile->tile.getPosition().equals(getLocation())).findFirst().get();
        Point newLocation = getLocation();
        if(currentTile.getNutritionContent() < getNutritionExpenses())
        {
            Comparator<Tile> cmp = new Comparator<Tile>() {
                @Override
                public int compare(Tile o1, Tile o2) {
                    Double a = o1.getNutritionContent();
                    Double b = o2.getNutritionContent();
                    return a.compareTo(b);
                }
            };
            newLocation = list.stream().max((t1, t2)->cmp.compare(t1,t2)).get().getPosition();

        }
        possibleMoves.add(newLocation);
    }


    @Override
    protected void survive(List<Point> possibleMoves)
    {
        List<Animal> carnivores = MasterData.animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        carnivores = carnivores.stream().filter(animal->animal instanceof AnimalCarnivore).collect(Collectors.toList());

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

}
