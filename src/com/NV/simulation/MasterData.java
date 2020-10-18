package com.NV.simulation;

import com.NV.simulation.controllers.AddAnimalController;
import com.NV.simulation.controllers.MainController;
import com.NV.simulation.controllers.TileEditController;
import com.NV.simulation.managers.AnimalManager;
import com.NV.simulation.managers.WeatherManager;
import com.NV.simulation.map.Map;
import com.NV.simulation.weather.Wind;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.util.Random;

public final class MasterData {
    public static Map map = new Map();
    public static AnimalManager animalManager = new AnimalManager();
    public static Random random = new Random();
    public static StringBuilder stringBuilder = new StringBuilder();
    public static Window mainWindow = null;
    public static Pane tileMap;//Just temporary
    public static MainController mainUIController;
    public static TileEditController tileEditController;
    public static AddAnimalController addAnimalController;
    public static WeatherManager weatherManager = new WeatherManager();

}
