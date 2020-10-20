package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.formaters.NumberTextFormatter;
import com.NV.simulation.graphics.TextureStorage;
import com.NV.simulation.map.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class AddAnimalController {

    @FXML
    private Rectangle animalTextureSpace;

    @FXML
    private ChoiceBox<String> animalTypeChoiceBox;

    @FXML
    private TextField speedTextField;

    @FXML
    private TextField hungerTextField;

    @FXML
    private TextField sensingRangeTextField;

    @FXML
    private TextField survivalDriveTextField;

    @FXML
    private TextField reproductionDriveTextField;

    @FXML
    private TextField mutationRateTextField;



    private String type = "Rabbit";

    public void initialize()
    {
        animalTypeChoiceBox.getItems().addAll("Rabbit","Wolf","Fox");
        animalTypeChoiceBox.getSelectionModel().select("Rabbit");
        onTypeChange();

        speedTextField.setTextFormatter(NumberTextFormatter.getFormatter());
        hungerTextField.setTextFormatter(NumberTextFormatter.getFormatter());
        sensingRangeTextField.setTextFormatter(NumberTextFormatter.getFormatter());
        survivalDriveTextField.setTextFormatter(NumberTextFormatter.getFormatter());
        reproductionDriveTextField.setTextFormatter(NumberTextFormatter.getFormatter());
        mutationRateTextField.setTextFormatter(NumberTextFormatter.getFormatter());
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

    @FXML
    public void onApply()
    {
        System.out.println("applied");
        MasterData.animalPlacer.placer.setVisible(true);
    }

}
