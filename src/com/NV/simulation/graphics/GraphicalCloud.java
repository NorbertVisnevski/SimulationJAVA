package com.NV.simulation.graphics;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.weather.Cloud;
import com.NV.simulation.weather.ContinentalCloud;
import com.NV.simulation.weather.OceanianCloud;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GraphicalCloud extends Rectangle{
    public GraphicalCloud(Cloud cloud) {
            double x = cloud.getLocation().x * GraphicSettings.TILE_WIDTH + (cloud.getLocation().y % 2) * GraphicSettings.n + GraphicSettings.xStartOffset;
            double y = cloud.getLocation().y * GraphicSettings.TILE_HEIGHT * 0.75 + GraphicSettings.yStartOffset;
            setX(x+2);
            setY(y-8);
            setHeight(12);
            setWidth(50);
            if(cloud instanceof ContinentalCloud) {
                setFill(new ImagePattern(TextureStorage.cloud2));
            }
            else if(cloud instanceof OceanianCloud)
            {
                setFill(new ImagePattern(TextureStorage.cloud1));
            }

            setOpacity(0.8);

    }
}
