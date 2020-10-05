package com.NV.simulation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController{

    @FXML
    public Pane tilemap;

    @FXML
    public void actionWindow(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/NV/simulation/UI/SimpleActions.fxml"));
            Stage secondStage = new Stage();
            secondStage.initOwner(tilemap.getScene().getWindow());
            secondStage.initStyle(StageStyle.UTILITY);
            secondStage.setTitle("Simple Actions");
            secondStage.setResizable(false);
            secondStage.setScene(new Scene(root));
            secondStage.show();
        }
        catch(Exception e){}

    }
    @FXML
    public void statWindow(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/NV/simulation/UI/Stats.fxml"));
            Stage secondStage = new Stage();
            secondStage.initOwner(tilemap.getScene().getWindow());
            secondStage.initStyle(StageStyle.UTILITY);
            secondStage.setTitle("Stats");
            secondStage.setResizable(false);
            secondStage.setScene(new Scene(root));
            secondStage.show();
        }
        catch(Exception e){}

    }
}
