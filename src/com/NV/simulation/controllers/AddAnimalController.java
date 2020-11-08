package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.exceptions.UnknownAnimalException;
import com.NV.simulation.formaters.NumberTextFormatter;
import com.NV.simulation.graphics.TextureStorage;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void initialize()
    {
        animalTypeChoiceBox.getSelectionModel().select(0);
        onTypeChange();

        speedTextField.setTextFormatter(new NumberTextFormatter().getFormatter());
        hungerTextField.setTextFormatter(new NumberTextFormatter().getFormatter());
        sensingRangeTextField.setTextFormatter(new NumberTextFormatter().getFormatter());
        survivalDriveTextField.setTextFormatter(new NumberTextFormatter().getFormatter());
        reproductionDriveTextField.setTextFormatter(new NumberTextFormatter().getFormatter());
        mutationRateTextField.setTextFormatter(new NumberTextFormatter().getFormatter());
    }

    public void onTypeChange()
    {
        switchType(animalTypeChoiceBox.getSelectionModel().getSelectedItem());
    }

    private void switchType(String type)
    {
        switch(type)
        {
            case "Rabbit":
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.rabbit, 0, 0, 1, 1, true));
                break;
            case "Wolf":
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.wolf, 0, 0, 1, 1, true));
                break;
            case "Fox":
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.fox, 0, 0, 1, 1, true));
                break;
            case "Deer":
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.deer, 0, 0, 1, 1, true));
                break;
            default:
                animalTextureSpace.setFill(Color.DEEPPINK);
        }
    }

    @FXML
    public void onApply()
    {
        MasterData.animalPlacer.enable();
    }

    public Animal createAnimal(Point location) throws UnknownAnimalException
    {
        Animal animal = null;
        switch(animalTypeChoiceBox.getSelectionModel().getSelectedItem())
        {
            case "Rabbit":
                animal = new AnimalRabbit();
                break;
            case "Wolf":
                animal = new AnimalWolf();
                break;
            case "Fox":
                animal = new AnimalFox();
                break;
            case "Deer":
                animal = new AnimalDeer();
                break;
            default:
                throw new UnknownAnimalException("Can't create animal of type: " + animalTypeChoiceBox.getSelectionModel().getSelectedItem());
        }
        animal.setHunger(Double.parseDouble(hungerTextField.getText()));
        animal.setReproductionDrive(Double.parseDouble(reproductionDriveTextField.getText()));
        animal.setSurvivalDrive(Double.parseDouble(survivalDriveTextField.getText()));
        animal.setSpeed(Double.parseDouble(speedTextField.getText()));
        animal.setSensesRange(Double.parseDouble(sensingRangeTextField.getText()));
        animal.setMutationRate(Double.parseDouble(mutationRateTextField.getText()));
        animal.setLocation(new Point(location));

        return animal;
    }

}
