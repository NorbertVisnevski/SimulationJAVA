package com.NV.simulation.managers.animal;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.Animal;
import com.NV.simulation.exceptions.UnknownAnimalException;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.graphics.entities.GraphicAnimal;
import com.NV.simulation.graphics.entities.GraphicalAnimalPlacer;

import java.awt.*;

public class AnimalPlacer {

    public final GraphicalAnimalPlacer placer = new GraphicalAnimalPlacer();

    private boolean readyToPlace;

    public void update()
    {
        placer.setLocation(new Point(MasterData.mainUIController.getMouseLocation().x, MasterData.mainUIController.getMouseLocation().y));
    }

    public boolean readyToPlace()
    {
        return readyToPlace;
    }

    public void enable()
    {
        readyToPlace = true;
        placer.setVisible(true);
    }
    public void disable()
    {
        readyToPlace = false;
        placer.setVisible(false);
    }

    public void placeAnimal(Point position) throws UnknownAnimalException
    {
        Animal animal = MasterData.addAnimalController.createAnimal(position);
        Application.addCallbackFunction(() -> {
            MasterData.animalManager.add(animal);
            Application.animalGroup.getChildren().add(new GraphicAnimal(animal));
            ((GraphicAnimal)Application.animalGroup.getChildren().get(Application.animalGroup.getChildren().size()-1)).shufflePosition();
        });
    }
}
