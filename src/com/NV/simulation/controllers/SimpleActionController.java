package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;
import javafx.fxml.FXML;

public class SimpleActionController {

    @FXML
    public void nextSimulationTurn()
    {
        MasterData.animalManager.update();
        Application.updateSimulationState();
    }
}
