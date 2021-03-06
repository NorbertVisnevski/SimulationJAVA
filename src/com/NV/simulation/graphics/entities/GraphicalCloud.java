package com.NV.simulation.graphics.entities;

import com.NV.simulation.graphics.GraphicSettings;
import com.NV.simulation.graphics.TextureStorage;
import com.NV.simulation.weather.clouds.Cloud;
import com.NV.simulation.weather.clouds.ContinentalCloud;
import com.NV.simulation.weather.clouds.OceanianCloud;
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
