package com.NV.simulation.managers;

import com.NV.simulation.animals.*;
import com.NV.simulation.animals.*;
import com.NV.simulation.map.Map;
import com.NV.simulation.map.NutritiousTile;
import com.NV.simulation.map.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalManager {

    private Map map;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private List<Animal> animalCollection = new ArrayList<>();


    private static Comparator<Animal> listSorter = new Comparator<>() {
        @Override
        public int compare(Animal o1, Animal o2) {
            if(o1 == o2)
                return 0;
            Double d1 = o2.getSpeed();
            Double d2 = o1.getSpeed();
            int res =  d1.compareTo(d2);
            if(res == 0)
                res = 1;
            return res;
        }
    };

    public List<Animal> getAnimalList()
    {
        return new ArrayList<>(animalCollection);
    }

    public List<Animal> getAnimalsInRange(Point origin, double radius)
    {
        return new ArrayList<>(animalCollection.stream().
                filter(animal->Math.floor(origin.distance(animal.getLocation()))<=radius).
                filter(animal->!animal.isDead()).
                collect(Collectors.toList()));
    }

    public List<Animal> getAnimalsAt(Point origin)
    {
        return new ArrayList<>(animalCollection.stream().
                filter(animal->animal.getLocation().equals(origin)).
                filter(animal->!animal.isDead()).
                collect(Collectors.toList()));
    }

    public void add(Animal animal){
        animalCollection.add(animal);
        animalCollection.sort(listSorter);
    }
    public void add(Collection<Animal> animals){
        animalCollection.addAll(animals);
        animalCollection.sort(listSorter);
    }

    public void update() {
        List<Animal> newAnimals = new ArrayList<>();
        List<Animal> deadAnimals = new ArrayList<>();
        //TODO fix this!
        try {
            for (Animal animal : animalCollection) {
                //kill if animal is hungry to death
                if (animal.getHunger() > 100.0) {
                    //animalCollection.remove(animal);
                    animal.setDead();
                    deadAnimals.add(animal);
                    System.out.println(animal);
                    continue;
                }

                Point point = animal.calculateMove();
                if(map.getTileAt(point).isImpassible())
                {
                    point = map.getTileNeighbours(animal.getLocation(),true).get(0).getPosition();
                }
                if (animal instanceof AnimalHerbivore) {
                    if (point.equals(animal.getLocation())) {
                        Tile tile = map.getTileAt(point);
                        if (tile instanceof NutritiousTile) {
                            ((NutritiousTile) tile).herbivoreInteraction((AnimalHerbivore) animal);
                        }
                    }
                }

                //update hunger
                if (!animal.getLocation().equals(point)) {
                    animal.setHunger(animal.getHunger() + map.getTileAt(point).getTravelDifficulty());
                }
                animal.setLocation(point);
                animal.setHunger(animal.getHunger() + animal.getNutritionExpenses());



                if (animal instanceof AnimalCarnivore) {
                    List<Animal> animalsAtLocation = getAnimalsAt(animal.getLocation());
                    Animal food = null;
                    if (animal instanceof AnimalWolf)
                        food = animalsAtLocation.stream().filter(anim -> anim instanceof AnimalRabbit || anim instanceof AnimalFox).findFirst().orElse(null);
                    if (animal instanceof AnimalFox)
                        food = animalsAtLocation.stream().filter(anim -> anim instanceof AnimalRabbit).findFirst().orElse(null);

                    if (food != null) {
                        animal.setHunger(0.0);
                        food.setDead();
                        deadAnimals.add(food);
                    }
                }

                //reproduce
                if(animal.getHunger()<50.0) {

                    List<Animal> animalsAtLocation = getAnimalsAt(animal.getLocation());
                    Animal possibleMate = animalsAtLocation.stream().filter(anim -> anim.getClass() == animal.getClass()).filter(anim->anim.getHunger()<50.0).filter(anim->anim!=animal).findFirst().orElse(null);

                    if (possibleMate != null) {
                        newAnimals.add(animal.mateWith(possibleMate));
                    }
                }
            }
            animalCollection.removeAll(deadAnimals);
            add(newAnimals);
        }
        catch(Exception e)
        {System.out.println(e);}
    }
}
