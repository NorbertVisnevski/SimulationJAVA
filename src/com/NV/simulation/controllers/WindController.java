package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.graphics.TextureStorage;
import com.NV.simulation.weather.Wind;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class WindController {
    @FXML
    private ImageView imageView;

    private String windDirection;

    private AnimationTimer animationTimer;

    public void stop()
    {
        animationTimer.stop();
    }

    @FXML
    public void initialize() {
        imageView.setImage(TextureStorage.arrow);
        imageView.setSmooth(true);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                windDirection= MasterData.weatherManager.getWindDirection();
                imageView.setRotate((imageView.getRotate()+(calculateAngle(windDirection)-imageView.getRotate())*75/360));
            }
        };
        animationTimer.start();
    }

    private int calculateAngle(String str)
    {
        switch(str)
        {
            case Wind.WindDirections.SOUTH_WEST:
                return 0;
            case Wind.WindDirections.SOUTH_EAST:
                return -90;
            case Wind.WindDirections.EAST:
                return -135;
            case Wind.WindDirections.NORTH_EAST:
                return 180;
            case Wind.WindDirections.NORTH_WEST:
                return 90;
            case Wind.WindDirections.WEST:
                return 45;
        }
        return 0;
    }
}
