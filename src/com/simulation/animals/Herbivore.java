package com.simulation.animals;

import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;
import com.simulation.map.NutritiousTile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Herbivore extends Animal {

    public Herbivore(Map map, AnimalManager animalManager) {
        super(map, animalManager);
    }

    public Herbivore(Map map, AnimalManager animalManager, double hunger, double reproductionDrive, double survivalDrive, String sex, double speed, double sensesRange, double mutationRate, Point location) {
        super(map, animalManager, hunger, reproductionDrive, survivalDrive, sex, speed, sensesRange, mutationRate, location);
    }

    @Override
    protected void findFood(List<Point> possibleMoves)
    {
        List<NutritiousTile> list = (ArrayList<NutritiousTile>)(ArrayList<?>)tileOptions.stream().filter(tile->(tile instanceof NutritiousTile)).collect(Collectors.toList());
        Collections.shuffle(list);
        double nutritionExpenses = getNutritionExpenses();

        if(list.size()==0)
        {
            Random rand = new Random();
            possibleMoves.add(tileOptions.get(rand.nextInt()%tileOptions.size()).getPosition());
            return;
        }
        NutritiousTile currentTile = list.stream().filter(tile->tile.getPosition().equals(getLocation())).findFirst().get();
        Point newLocation = getLocation();
        if(currentTile.getNutritionContent() < getNutritionExpenses())
        {
            Comparator<NutritiousTile> cmp = new Comparator<NutritiousTile>() {
                @Override
                public int compare(NutritiousTile o1, NutritiousTile o2) {
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
        List<Animal> carnivores = animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        carnivores = carnivores.stream().filter(animal->animal instanceof Carnivore).collect(Collectors.toList());

        if(carnivores.size()==0)
        {
            Random rand = new Random();
            possibleMoves.add(tileOptions.get(rand.nextInt()%tileOptions.size()).getPosition());
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
