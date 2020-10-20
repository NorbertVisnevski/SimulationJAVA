package com.NV.simulation.managers;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.GraphicSettings;
import com.NV.simulation.graphics.GraphicalAnimalPlacer;
import com.NV.simulation.map.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AnimalPlacer {

    public GraphicalAnimalPlacer placer = new GraphicalAnimalPlacer();

    public Point position;

    public boolean readyToPlace;

    public boolean canPlaceHere()
    {
        return true;
    }

    public AnimalPlacer()
    {
        placer.setVisible(true);
    }

    public void update()
    {
        placer.setLocation(new Point(MasterData.mainUIController.getMouseLocation().x, MasterData.mainUIController.getMouseLocation().y));
    }
}
