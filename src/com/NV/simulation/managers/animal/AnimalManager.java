package com.NV.simulation.managers.animal;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.exceptions.AnimalHybridException;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.graphics.dialogs.ErrorDialog;
import com.NV.simulation.managers.CollectionManager;
import com.NV.simulation.managers.file.AsyncLogHandler;
import com.NV.simulation.managers.file.ErrorLogger;
import com.NV.simulation.tile.Tile;

import java.awt.*;
import java.io.File;
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
        return animalCollection.stream().
                filter(animal -> Math.floor(origin.distance(animal.getLocation())) <= radius).
                filter(animal -> !animal.isDead()).collect(Collectors.toList());
    }

    public List<Animal> getAnimalsAt(Point origin)
    {
        return animalCollection.stream().
                filter(animal -> animal.getLocation().equals(origin)).
                filter(animal -> !animal.isDead()).collect(Collectors.toList());
    }

    public void add(Animal animal){
        if(animal!=null) {
            animalCollection.add(animal);
            animalCollection.sort(listSorter);
        }
    }
    public void add(Collection<Animal> animals){
        animalCollection.addAll(animals.stream().filter(Objects::nonNull).collect(Collectors.toList()));
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
        try {
            for (Animal animal : animalCollection) {

                if (animal.isDead()) {
                    deadAnimals.add(animal);
                    continue;
                }

                //kill if animal is hungry to death
                if (animal.getHunger() > 100.0) {
                    animal.setDead();
                    deadAnimals.add(animal);
                    continue;
                }

                //update hunger
                animal.setHunger(animal.getHunger() + animal.getNutritionExpenses());
                Point point = animal.calculateMove();
                if (MasterData.map.getTileAt(point).isImpassible()) {
                    point = MasterData.map.getTileNeighbours(animal.getLocation(), true).get(0).getPosition();
                }
                if (animal instanceof AnimalHerbivore) {
                    if (point.equals(animal.getLocation())) {
                        Tile tile = MasterData.map.getTileAt(point);
                        tile.herbivoreInteraction((AnimalHerbivore) animal);
                    }
                }

                if (!animal.getLocation().equals(point)) {
                    animal.setHunger(animal.getHunger() + MasterData.map.getTileAt(point).getTravelDifficulty());
                }
                animal.setLocation(point);

                if (animal instanceof AnimalCarnivore) {
                    List<Animal> animalsAtLocation = getAnimalsAt(animal.getLocation());
                    for(Animal anim:animalsAtLocation)
                    {
                        if(animal != anim && ((AnimalCarnivore) animal).canConsume(anim)) {
                            animal.setHunger(0.0);
                            anim.setDead();
                            deadAnimals.add(anim);
                            break;
                        }
                    }
                }

                //reproduce
                if (animal.getHunger() <= 50.0) {
                    List<Animal> animalsAtLocation = getAnimalsAt(animal.getLocation());
                    for(Animal anim : animalsAtLocation)
                    {
                        if(animal != anim && anim.getHunger() <= 50) {
                            try{
                                newAnimals.add(animal.mateWith(anim));
                                break;
                            }
                            catch (AnimalHybridException ignored) { }
                        }
                    }
                }

            }
        }
        catch(NullPointerException e)
        {
            AsyncLogHandler log = new ErrorLogger();
            log.append(new File("log.txt"), "Animal manager error\n"+e);
            Iterator<Animal> i = animalCollection.iterator();
            while (i.hasNext()) {
                if(i.next() == null)
                    i.remove();
            }
            update();
            return;
        }
        catch(Exception e)
        {
            AsyncLogHandler log = new ErrorLogger();
            log.append(new File("log.txt"), "Animal manager error\n"+e);
            Application.addCallbackFunction(()->new ErrorDialog("Simulation Error",e.getMessage()));
        }
        animalCollection.removeAll(deadAnimals);
        add(newAnimals);

    }
}
