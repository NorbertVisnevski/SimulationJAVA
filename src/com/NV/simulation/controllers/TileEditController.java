package com.NV.simulation.controllers;

import com.NV.simulation.formaters.NumberTextFormatter;
import com.NV.simulation.graphics.GraphicTile;
import com.NV.simulation.map.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class TileEditController {

    @FXML
    private Group mainGroup;

    @FXML
    private Pane tilePane;

    @FXML
    private ChoiceBox<String> terrainType;

    @FXML
    private CheckBox impassible;

    @FXML
    private TextField travelDifficulty;

    @FXML
    private TextField nutritionContent;

    private Tile tile;
    private GraphicTile gTile;

    private List<Node> group;

    @FXML
    public void initialize()
    {
        group = new ArrayList<>(mainGroup.getChildren());
        mainGroup.getChildren().clear();
        Label l = new Label("No tile selected");
        l.setLayoutY(25);
        l.setLayoutX(15);
        mainGroup.getChildren().add(l);
        terrainType.getItems().addAll(
                Tile.TerrainTypes.PLANES,
                Tile.TerrainTypes.FOREST,
                Tile.TerrainTypes.DESERT,
                Tile.TerrainTypes.WATER,
                Tile.TerrainTypes.MOUNTAINS,
                Tile.TerrainTypes.MARSH,
                Tile.TerrainTypes.FROZEN_WASTELAND
                );

        travelDifficulty.setTextFormatter(new NumberTextFormatter().getFormatter());
        nutritionContent.setTextFormatter(new NumberTextFormatter().getFormatter());

    }

    @FXML
    public void onApply(ActionEvent event)
    {
        tile.setTerrainType(terrainType.getSelectionModel().getSelectedItem());
        tile.setImpassible(impassible.isSelected());
        tile.setTravelDifficulty(Double.parseDouble(travelDifficulty.getText()));
        tile.setNutritionContent(Double.parseDouble(nutritionContent.getText()));
        gTile.updateToolTip();
        setTile(tile,gTile);
    }


    public void setTile(Tile tile, GraphicTile gTile)
    {
        if(mainGroup.getChildren().size()==1)
        {
            mainGroup.getChildren().clear();
            mainGroup.getChildren().addAll(group);
            tilePane.getChildren().add(new GraphicTile(tile,0,0));
        }
        this.tile = tile;
        this.gTile = gTile;
        ((GraphicTile)tilePane.getChildren().get(0)).update(tile);
        terrainType.getSelectionModel().select(tile.getTerrainType());
        impassible.setSelected(tile.isImpassible());
        travelDifficulty.setText(((Double)tile.getTravelDifficulty()).toString());
        nutritionContent.setText(((Double)tile.getNutritionContent()).toString());

    }

    public void clear()
    {
        tile = null;
        mainGroup.getChildren().clear();
        Label l = new Label("No tile selected");
        l.setLayoutY(25);
        l.setLayoutX(15);
        mainGroup.getChildren().add(l);
    }
}
