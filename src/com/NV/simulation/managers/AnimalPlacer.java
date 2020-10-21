package com.NV.simulation.managers;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.Animal;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.graphics.GraphicSettings;
import com.NV.simulation.graphics.GraphicalAnimalPlacer;
import com.NV.simulation.map.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AnimalPlacer {

    public GraphicalAnimalPlacer placer = new GraphicalAnimalPlacer();

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

    public void placeAnimal(Point position) {
        Animal animal = MasterData.addAnimalController.createAnimal(position);
        if (animal != null) {
            Application.addCallbackFunction(() -> {
                MasterData.animalManager.add(animal);
                Application.updateSimulationState();
            });
        }
    }
}
