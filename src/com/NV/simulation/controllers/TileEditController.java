package com.NV.simulation.controllers;

import com.NV.simulation.graphics.GraphicTile;
import com.NV.simulation.map.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class TileEditController {

    @FXML
    private Pane tilePane;

    private Tile tile;

    public void setTile(Tile tile)
    {
        this.tile = tile;
        tilePane.getChildren().clear();
        tilePane.getChildren().add(new GraphicTile(tile));
    }
}
