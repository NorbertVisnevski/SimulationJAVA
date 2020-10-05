package com.NV.simulation;

import com.NV.simulation.animals.Animal;
import com.NV.simulation.animals.AnimalFox;
import com.NV.simulation.animals.AnimalRabbit;
import com.NV.simulation.animals.AnimalWolf;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.managers.AnimalManager;
import com.NV.simulation.map.Map;
import com.NV.simulation.map.NutritiousTile;
import com.NV.simulation.map.Tile;
import com.NV.simulation.weather.Wind;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // simulation init
        for(int i = 0; i< 40; ++i)
        {
            for (int j=0; j< 20; ++j)
            {
                MasterData.map.add(new NutritiousTile(new Point(i,j), Tile.TerrainTypes.PLANES, false, 1.0, 10.0));
            }
        }
        for(int i = 0; i< 40; ++i)
            MasterData.map.add(new Tile(new Point(i,20), Tile.TerrainTypes.MOUNTAINS, true, 1.0));
        for(int i = 0; i< 40; ++i)
            MasterData.map.add(new Tile(new Point(i,21), Tile.TerrainTypes.PLANES, false, 1.0));
        for(int i = 0; i< 40; ++i)
            MasterData.map.add(new Tile(new Point(i,22), Tile.TerrainTypes.WATER, true, 1.0));

        MasterData.weatherManager.linkToMap(MasterData.map);

        MasterData.animalManager.setMap(MasterData.map);

        Animal a;
        a = new AnimalRabbit();
        a.setLocation(new Point(20,9));
        MasterData.animalManager.add(a);
        a = new AnimalRabbit();
        a.setLocation(new Point(11,9));
        a.setSensesRange(20);
        a.setReproductionDrive(20);
        MasterData.animalManager.add(a);
        a = new AnimalWolf();
        a.setLocation(new Point(18,9));
        MasterData.animalManager.add(a);
        a = new AnimalFox();
        a.setLocation(new Point(0,0));
        MasterData.animalManager.add(a);
        a = new AnimalRabbit();
        a.setLocation(new Point(22,9));
        MasterData.animalManager.add(a);
        a = new AnimalRabbit();
        a.setLocation(new Point(11,9));
        a.setSensesRange(10);
        MasterData.animalManager.add(a);

        javafx.application.Application.launch(Application.class,args);
    }
}
