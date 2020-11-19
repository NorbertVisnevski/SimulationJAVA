package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.threads.ManagerThread;
import com.NV.simulation.threads.ThreadControlFlags;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;

public class SimpleActionController {

    @FXML
    private Button btn1turn;

    @FXML
    private Slider simulationSpeedSlider;

    @FXML
    public void initialize()
    {
        ThreadControlFlags.simulationSpeed = (int)simulationSpeedSlider.getMax()-(int)simulationSpeedSlider.getValue();
        simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

            ThreadControlFlags.simulationSpeed = (int)simulationSpeedSlider.getMax()-newValue.intValue();
        });
    }

    @FXML
    public void nextSimulationTurn(ActionEvent event)
    {
        MasterData.animalManager.update();
        MasterData.map.update();
        MasterData.weatherManager.update();
        Application.updateSimulationState();
        Application.shuffleEntities();
    }

    @FXML
    public void onRun()
    {
        if(MasterData.managerThread.isInterrupted() || MasterData.managerThread.getState().equals(Thread.State.NEW))
        {
            MasterData.managerThread = new ManagerThread();
            MasterData.managerThread.start();
        }
    }
    @FXML
    public void onStop()
    {
        if(MasterData.managerThread.isAlive())
        {
            MasterData.managerThread.interrupt();
        }
    }
}
