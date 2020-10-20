package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.managers.AsyncFileHandler;
import com.NV.simulation.managers.MapFileHandler;
import com.NV.simulation.managers.SimulationFileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;

public class MainController{

    @FXML
    public Pane tilemap;

    @FXML
    public VBox actionBar;

    private Point mouseLocation = new Point();

    @FXML
    public void onMouseMoved(MouseEvent event)
    {
        mouseLocation = new Point((int)event.getX(), (int)event.getY());
    }

    public Point getMouseLocation()
    {
        return mouseLocation;
    }

    @FXML
    public void statWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/Stats.fxml"));
            Parent root = loader.load();
            Stage secondStage = new Stage();
            secondStage.initOwner(MasterData.mainWindow);
            secondStage.initStyle(StageStyle.UTILITY);
            secondStage.setTitle("Stats");
            secondStage.setResizable(false);
            secondStage.setScene(new Scene(root));
            secondStage.show();
            MasterData.statsController = loader.getController();
            secondStage.setOnCloseRequest(e -> MasterData.statsController.stop());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void onOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SIM", "*.sim"),
                new FileChooser.ExtensionFilter("MAP", "*.map"));
        File file = fileChooser.showOpenDialog(MasterData.mainWindow);
        if(file != null)
        {
            String extension = "";

            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i+1);
            }
            if(extension.equals("map"))
            {
                AsyncFileHandler mapReader = new MapFileHandler();
                mapReader.readAsync(file);
            }
            else if(extension.equals("sim"))
            {
                AsyncFileHandler simReader = new SimulationFileHandler();
                simReader.readAsync(file);
            }
        }
    }

    @FXML
    public void onSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SIM", "*.sim"),
                new FileChooser.ExtensionFilter("MAP", "*.map"));
        File file = fileChooser.showSaveDialog(MasterData.mainWindow);
        if(file != null)
        {
            String extension = "";

            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i+1);
            }
            if(extension.equals("map"))
            {
                AsyncFileHandler mapWriter = new MapFileHandler();
                mapWriter.writeAsync(file);
            }
            else if(extension.equals("sim"))
            {
                AsyncFileHandler simWriter = new SimulationFileHandler();
                simWriter.writeAsync(file);
            }
        }
    }

    @FXML
    public void quit(ActionEvent event)
    {
        ((Stage) MasterData.mainWindow).close();
    }
}
