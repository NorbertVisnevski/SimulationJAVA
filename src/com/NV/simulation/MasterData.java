package com.NV.simulation;

import com.NV.simulation.controllers.AddAnimalController;
import com.NV.simulation.controllers.MainController;
import com.NV.simulation.controllers.StatsController;
import com.NV.simulation.controllers.TileEditController;
import com.NV.simulation.managers.animal.AnimalManager;
import com.NV.simulation.managers.animal.AnimalPlacer;
import com.NV.simulation.managers.weather.WeatherManager;
import com.NV.simulation.managers.map.Map;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.io.File;
import java.util.Random;

public abstract class MasterData {

    public static Map map = new Map();
    public static AnimalManager animalManager = new AnimalManager();
    public static Random random = new Random();
    public static StringBuilder stringBuilder = new StringBuilder();
    public static Window mainWindow = null;
    public static Pane tileMap;
    public static MainController mainUIController;
    public static StatsController statsController;
    public static TileEditController tileEditController;
    public static AddAnimalController addAnimalController;
    public static WeatherManager weatherManager = new WeatherManager();
    public static AnimalPlacer animalPlacer = new AnimalPlacer();
    public static File currentFile = null;
    public static void clearManagers()
    {
        animalManager.clear();
        map.clear();
        weatherManager.clear();
        tileEditController.clear();
    }

}
