package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.graphics.dialogs.ErrorDialog;
import com.NV.simulation.graphics.dialogs.NewMapDialog;
import com.NV.simulation.managers.file.AsyncFileHandler;
import com.NV.simulation.managers.file.MapFileHandler;
import com.NV.simulation.managers.file.SimulationFileHandler;
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
import java.io.IOException;

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
    public void statWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/resources/UI/Stats.fxml"));
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
        } catch(IOException e) {
            Application.addCallbackFunction(()->{new ErrorDialog("UI Error","Unable to locate Stats.fxml");});
        }
        catch(Exception e) {
            Application.addCallbackFunction(()->{new ErrorDialog("Error","Critical application error");});
        }
    }

    @FXML
    public void onSave()
    {
        //TODO add normal save
        onSaveAs();
    }

    @FXML
    public void onOpen() {
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
    public void onSaveAs() {
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
    public void onNew()
    {
        new NewMapDialog();
    }

    @FXML
    public void quit()
    {
        ((Stage) MasterData.mainWindow).close();
    }
}
