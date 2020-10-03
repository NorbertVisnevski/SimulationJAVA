package com.simulation;

import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;
import javafx.scene.layout.Pane;

import java.util.Random;

public final class MasterData {
    public static Map map;
    public static AnimalManager animalManager;
    public static Random random = new Random();
    public static Pane tilemap;

}
