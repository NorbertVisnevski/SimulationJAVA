package com.NV.simulation.managers.animal;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.managers.CollectionManager;
import com.NV.simulation.tile.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalManager implements CollectionManager<Animal> {

    private final List<Animal> animalCollection = new ArrayList<>();

    private static final Comparator<Animal> listSorter = new Comparator<>() {
        @Override
        public int compare(Animal o1, Animal o2) {
            Double d1 = o2.getSpeed();
            Double d2 = o1.getSpeed();

            return d1.compareTo(d2);
        }
    };

    public List<Animal> getList()
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
    public void clear()
    {
        animalCollection.forEach(Animal::setDead);
        animalCollection.clear();
    }

    public void update() {
        List<Animal> newAnimals = new ArrayList<>();
        List<Animal> deadAnimals = new ArrayList<>();
        //TODO fix this!
        try {
            for (Animal animal : animalCollection) {

                if(animal.isDead())
                {
                    deadAnimals.add(animal);
                    continue;
                }

                //kill if animal is hungry to death
                if (animal.getHunger() > 100.0) {
                    animal.setDead();
                    deadAnimals.add(animal);
                    continue;
                }

                Point point = animal.calculateMove();
                if(MasterData.map.getTileAt(point).isImpassible())
                {
                    point = MasterData.map.getTileNeighbours(animal.getLocation(),true).get(0).getPosition();
                }
                if (animal instanceof AnimalHerbivore) {
                    if (point.equals(animal.getLocation())) {
                        Tile tile = MasterData.map.getTileAt(point);
                        tile.herbivoreInteraction((AnimalHerbivore) animal);
                    }
                }

                //update hunger
                if (!animal.getLocation().equals(point)) {
                    animal.setHunger(animal.getHunger() + MasterData.map.getTileAt(point).getTravelDifficulty());
                }
                animal.setLocation(point);
                animal.setHunger(animal.getHunger() + animal.getNutritionExpenses());



                if (animal instanceof AnimalCarnivore) {
                    List<Animal> animalsAtLocation = getAnimalsAt(animal.getLocation());
                    Animal food = null;
                    if (animal instanceof AnimalWolf)
                        food = animalsAtLocation.stream().filter(anim -> anim instanceof AnimalRabbit || anim instanceof AnimalFox || anim instanceof AnimalDeer).findFirst().orElse(null);
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
        {System.out.println("Animal manager" + e);}
    }
}
