package com.NV.simulation.graphics.entities;

import com.NV.simulation.MasterData;
import com.NV.simulation.tile.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.awt.*;

public class GraphicalAnimalPlacer extends Polygon {

    public GraphicalAnimalPlacer()
    {
        setVisible(false);
        getPoints().addAll(
                -10.0,-20.0,
                0.0,0.0,
                10.0,-20.0
        );
        setFill(Color.WHITE);
        setOpacity(0.5);
    }

    public void setLocation(Point p)
    {
        setLayoutX(p.x);
        setLayoutY(p.y);
    }

}
