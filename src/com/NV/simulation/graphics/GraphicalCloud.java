package com.NV.simulation.graphics;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.weather.Cloud;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GraphicalCloud extends Rectangle{
    GraphicalCloud(Cloud cloud) {
            double x = cloud.getLocation().x * GraphicSettings.TILE_WIDTH + (cloud.getLocation().y % 2) * GraphicSettings.n + GraphicSettings.xStartOffset;
            double y = cloud.getLocation().y * GraphicSettings.TILE_HEIGHT * 0.75 + GraphicSettings.yStartOffset;
            setX(x);
            setY(y);
            setHeight(8);
            setWidth(32);
            setFill(Color.LIGHTGREY);

    }
}
