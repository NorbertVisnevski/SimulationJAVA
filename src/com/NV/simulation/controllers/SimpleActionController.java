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
        ThreadControlFlags.getInstance().simulationSpeed = (int)simulationSpeedSlider.getMax()-(int)simulationSpeedSlider.getValue();
        simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

            ThreadControlFlags.getInstance().simulationSpeed = (int)simulationSpeedSlider.getMax()-newValue.intValue();
        });
    }

    @FXML
    public void nextSimulationTurn(ActionEvent event)
    {
        new Thread(()->{
            Thread animal = new Thread(()-> {
                MasterData.animalManager.update();
            });
            Thread map = new Thread(()-> {
                MasterData.map.update();
            });
            Thread weather = new Thread(()-> {
                MasterData.weatherManager.update();
            });
            animal.start();
            map.start();
            weather.start();
            try{
                animal.join();
                map.join();
                weather.join();
            }
            catch(InterruptedException ignored){}
            Application.addCallbackFunction(()->{
                Application.updateSimulationState();
                Application.shuffleEntities();
            });
        }).start();
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
