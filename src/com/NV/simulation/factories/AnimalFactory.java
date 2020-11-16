package com.NV.simulation.factories;

import com.NV.simulation.animals.*;
import com.NV.simulation.exceptions.UnknownAnimalException;

public class AnimalFactory {

    public static Animal newAnimal(String type) throws UnknownAnimalException
    {
        return switch (type) {
            case "Rabbit" -> new AnimalRabbit();
            case "Wolf" -> new AnimalWolf();
            case "Fox" -> new AnimalFox();
            case "Deer" -> new AnimalDeer();
            case "Coyote" -> new AnimalCoyote();
            default -> throw new UnknownAnimalException("Can't create animal of type: " + type);
        };
    }
}
