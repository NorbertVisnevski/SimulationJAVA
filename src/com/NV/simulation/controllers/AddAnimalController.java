package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.formaters.NumberTextFormatter;
import com.NV.simulation.graphics.TextureStorage;
import com.NV.simulation.weather.wind.Wind;
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

    private final static class Types{
        public static final String DEER = "Deer";
        public static final String RABBIT = "Rabbit";
        public static final String WOLF = "Wolf";
        public static final String FOX = "Fox";
        public static final List<String> collection = new ArrayList<>(Arrays.asList(DEER, RABBIT, WOLF,FOX));;
    }

    private String type = "Rabbit";

    public void initialize()
    {
        animalTypeChoiceBox.getItems().addAll(Types.collection);
        animalTypeChoiceBox.getSelectionModel().select("Rabbit");
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
        type = animalTypeChoiceBox.getSelectionModel().getSelectedItem();
        switchType();
    }

    private void switchType()
    {
        switch(type)
        {
            case Types.RABBIT:
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.rabbit, 0, 0, 1, 1, true));
                return;
            case Types.WOLF:
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.wolf, 0, 0, 1, 1, true));
                return;
            case Types.FOX:
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.fox, 0, 0, 1, 1, true));
                return;
            case Types.DEER:
                animalTextureSpace.setFill(new ImagePattern(TextureStorage.deer, 0, 0, 1, 1, true));
                return;
            default:
                animalTextureSpace.setFill(Color.DEEPPINK);
        }
    }

    @FXML
    public void onApply()
    {
        MasterData.animalPlacer.enable();
    }

    public Animal createAnimal(Point location)
    {
        switch(type)
        {
            case Types.RABBIT:
                return new AnimalRabbit(Double.parseDouble(hungerTextField.getText()),
                        Double.parseDouble(reproductionDriveTextField.getText()),
                        Double.parseDouble(survivalDriveTextField.getText()),
                        Double.parseDouble(speedTextField.getText()),
                        Double.parseDouble(sensingRangeTextField.getText()),
                        Double.parseDouble(mutationRateTextField.getText()),
                        new Point(location));
            case Types.WOLF:
                return new AnimalWolf(Double.parseDouble(hungerTextField.getText()),
                    Double.parseDouble(reproductionDriveTextField.getText()),
                    Double.parseDouble(survivalDriveTextField.getText()),
                    Double.parseDouble(speedTextField.getText()),
                    Double.parseDouble(sensingRangeTextField.getText()),
                    Double.parseDouble(mutationRateTextField.getText()),
                    new Point(location));
            case Types.FOX:
                return new AnimalFox(Double.parseDouble(hungerTextField.getText()),
                    Double.parseDouble(reproductionDriveTextField.getText()),
                    Double.parseDouble(survivalDriveTextField.getText()),
                    Double.parseDouble(speedTextField.getText()),
                    Double.parseDouble(sensingRangeTextField.getText()),
                    Double.parseDouble(mutationRateTextField.getText()),
                    new Point(location));
            case Types.DEER:
                return new AnimalDeer(Double.parseDouble(hungerTextField.getText()),
                        Double.parseDouble(reproductionDriveTextField.getText()),
                        Double.parseDouble(survivalDriveTextField.getText()),
                        Double.parseDouble(speedTextField.getText()),
                        Double.parseDouble(sensingRangeTextField.getText()),
                        Double.parseDouble(mutationRateTextField.getText()),
                        new Point(location));
            default:
                System.out.println("null animal");
                return null;
        }
    }

}
