package com.NV.simulation.exceptions;

import com.NV.simulation.animals.Animal;
import javafx.util.Pair;

public class AnimalHybridException extends Exception {

    private Pair<Animal, Animal> animals = null;

    public AnimalHybridException() {
        super();
    }

    public AnimalHybridException(String message) {
        super(message);
    }

    public AnimalHybridException(String message, Animal animal1, Animal animal2)
    {
        super(message);
        animals = new Pair<>(animal1,animal2);
    }

    Pair<Animal, Animal>getIncompatibleAnimals()
    {
        return animals;
    }
}

