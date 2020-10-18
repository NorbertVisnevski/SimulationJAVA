package com.NV.simulation.controllers;

import com.NV.simulation.graphics.TextureStorage;
import com.NV.simulation.map.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class AddAnimalController {

    @FXML
    private Rectangle animalTextureSpace;

    @FXML
    private ChoiceBox<String> animalTypeChoiceBox;

    private String type = "Rabbit";

    public void initialize()
    {
        animalTypeChoiceBox.getItems().addAll("Rabbit","Wolf","Fox");
        animalTypeChoiceBox.getSelectionModel().select("Rabbit");
        onTypeChange();
    }

    public void onTypeChange()
    {
        type = animalTypeChoiceBox.getSelectionModel().getSelectedItem();
        switchType();
    }

    private void switchType()
    {
        switch(type)
        {
            case "Rabbit":
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.rabbit, 0, 0, 1, 1, true));
                return;
            case "Wolf":
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.wolf, 0, 0, 1, 1, true));
                return;
            case "Fox":
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.fox, 0, 0, 1, 1, true));
                return;
            default:
                animalTextureSpace.setFill(Color.DEEPPINK);
        }
    }

}
