package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.TextureStorage;
import com.NV.simulation.weather.wind.Wind;
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
                windDirection = MasterData.weatherManager.getWindDirection();
                imageView.setRotate((imageView.getRotate()+(calculateAngle(windDirection)-imageView.getRotate())*75/360));
            }
        };
        animationTimer.start();
    }

    private int calculateAngle(String str)
    {
        return switch (str) {
            case Wind.WindDirections.SOUTH_WEST -> 0;
            case Wind.WindDirections.SOUTH_EAST -> -90;
            case Wind.WindDirections.EAST -> -135;
            case Wind.WindDirections.NORTH_EAST -> 180;
            case Wind.WindDirections.NORTH_WEST -> 90;
            case Wind.WindDirections.WEST -> 45;
            default -> 0;
        };
    }
}
