package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SimpleActionController {

    @FXML
    private Button btn1turn;

    @FXML
    private Button btn100turn;

    @FXML
    private Button btn1000turn;

    @FXML
    public void nextSimulationTurn(ActionEvent event)
    {
        int i = 0;
        if (event.getSource().equals(btn1turn))
            i = 1;
        else if (event.getSource().equals(btn100turn))
            i = 100;
        else if (event.getSource().equals(btn1000turn))
            i = 1000;
        for(int j=0; j<i; ++j)
        {
            MasterData.animalManager.update();
            MasterData.map.replenishGroundNutrience(0.01);
            MasterData.weatherManager.updateClouds();
            MasterData.wind.updateWind();
        }
        Application.updateSimulationState();
    }
}
