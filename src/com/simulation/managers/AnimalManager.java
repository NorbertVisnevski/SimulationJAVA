package com.simulation.managers;

import com.simulation.animals.*;
import com.simulation.map.Map;
import com.simulation.map.NutritiousTile;
import com.simulation.map.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalManager {

    private com.simulation.map.Map map;

    public com.simulation.map.Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private final Set<Animal> animalCollection = new TreeSet<>(new Comparator<>() {
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
    });

    public List<Animal> getAnimalList()
    {
        return new ArrayList<>(animalCollection);
    }

    public List<Animal> getAnimalsInRange(Point origin, double radius)
    {
        return new ArrayList<>(animalCollection.stream().
                filter(animal->Math.floor(origin.distance(animal.getLocation()))<=radius).
                collect(Collectors.toList()));
    }

    public void add(Animal animal){
        animalCollection.add(animal);
    }
    public void add(Collection<Animal> animals){
        animalCollection.addAll(animals);
    }

    public void update() {

        //TODO fix this!
        try {
            for (Iterator<Animal> it = animalCollection.iterator(); it.hasNext();) {
                Animal animal = it.next();
                //kill if animal is hungry to death
                if (animal.getHunger() > 100.0) {
                    //animalCollection.remove(animal);
                    it.remove();
                    continue;
                }

                Point point = animal.calculateMove();
                if(map.getTileAt(point).isImpassible())
                {
                    point = map.getTileNeighbours(animal.getLocation(),true).get(0).getPosition();
                }
                if (animal instanceof Herbivore) {
                    if (point.equals(animal.getLocation())) {
                        Tile tile = map.getTileAt(point);
                        if (tile instanceof NutritiousTile) {
                            ((NutritiousTile) tile).herbivoreInteraction((Herbivore) animal);
                        }
                    }
                }

                //update hunger
                if (!animal.getLocation().equals(point)) {
                    animal.setHunger(animal.getHunger() + map.getTileAt(point).getTravelDifficulty());
                }
                animal.setLocation(point);
                animal.setHunger(animal.getHunger() + animal.getNutritionExpenses());

                if (animal instanceof Carnivore) {
                    List<Animal> animalsAtLocation = getAnimalsInRange(point, 0.5);
                    Animal food = null;
                    if (animal instanceof Wolf)
                        food = animalsAtLocation.stream().filter(anim -> anim instanceof Rabbit || anim instanceof Fox).findFirst().orElse(null);
                    if (animal instanceof Fox)
                        food = animalsAtLocation.stream().filter(anim -> anim instanceof Rabbit).findFirst().orElse(null);

                    if (food != null) {
                        animal.setHunger(0.0);
                        animalCollection.remove(food);
                    }

                }

                //reproduce
                if(animal.getHunger()<50.0) {

                    List<Animal> animalsAtLocation = getAnimalsInRange(point, 0.5);
                    Animal possibleMate = null;

                    if (animal.getSex().equals("male")) {
                        possibleMate = animalsAtLocation.stream().filter(anim -> anim.getClass() == animal.getClass()).filter(anim -> anim.getSex().equals("female")).filter(anim->anim.getHunger()<50.0).findFirst().orElse(null);
                    } else if (animal.getSex().equals("female")) {
                        possibleMate = animalsAtLocation.stream().filter(anim -> anim.getClass() == animal.getClass()).filter(anim -> anim.getSex().equals("male")).filter(anim->anim.getHunger()<50.0).findFirst().orElse(null);
                    }


                    if (possibleMate != null) {
                        this.add(animal.mateWith(possibleMate));
                    }
                }
            }
        }
        catch(Exception e)
        {System.out.println(e.getCause());}
    }
}
