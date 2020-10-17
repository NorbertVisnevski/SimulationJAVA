package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController{

    @FXML
    public Pane tilemap;

    @FXML
    public VBox actionBar;

    @FXML
    public void actionWindow(ActionEvent event) {
//        try {
//            FXMLLoader loader  = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/SimpleActions.fxml"));
//            Parent root = loader.load();
//            Stage secondStage = new Stage();
//            secondStage.initOwner(MasterData.mainWindow);
//            secondStage.initStyle(StageStyle.UTILITY);
//            secondStage.setTitle("Simple Actions");
//            secondStage.setResizable(false);
//            secondStage.setScene(new Scene(root));
//            secondStage.show();
//        }
//        catch(Exception e){}

    }
    @FXML
    public void statWindow(ActionEvent event) {
        try {
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/Stats.fxml"));
            Parent root = loader.load();
            Stage secondStage = new Stage();
            secondStage.initOwner(MasterData.mainWindow);
            secondStage.initStyle(StageStyle.UTILITY);
            secondStage.setTitle("Stats");
            secondStage.setResizable(false);
            secondStage.setScene(new Scene(root));
            secondStage.show();
            secondStage.setOnCloseRequest(e->((StatsController)loader.getController()).stop());
        }
        catch(Exception e){}

    }
    @FXML
    public void openWindIndicator(ActionEvent event)
    {
//        try {
//            FXMLLoader loader  = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/WindIndicator.fxml"));
//            Parent root = loader.load();
//            Stage secondStage = new Stage();
//            secondStage.initOwner(MasterData.mainWindow);
//            secondStage.initStyle(StageStyle.UTILITY);
//            secondStage.setTitle("Wind");
//            secondStage.setResizable(false);
//            secondStage.setScene(new Scene(root));
//            secondStage.show();
//            secondStage.setOnCloseRequest(e->((WindController)loader.getController()).stop());
//        }
//        catch(Exception e){}
    }

    @FXML
    public void quit(ActionEvent event)
    {
        ((Stage) MasterData.mainWindow).close();
    }
}
