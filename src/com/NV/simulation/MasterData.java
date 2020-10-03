package com.NV.simulation;

import com.NV.simulation.controllers.MainController;
import com.NV.simulation.managers.AnimalManager;
import com.NV.simulation.map.Map;
import javafx.scene.layout.Pane;

import java.util.Random;

public final class MasterData {
    public static Map map;
    public static AnimalManager animalManager;
    public static Random random = new Random();
    public static Pane tileMap;
    public static MainController mainUIController;

}
