package com.simulation;

import com.simulation.animals.Animal;
import com.simulation.animals.Fox;
import com.simulation.animals.Rabbit;
import com.simulation.animals.Wolf;
import com.simulation.graphics.Application;
import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;
import com.simulation.map.NutritiousTile;
import com.simulation.map.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // simulation init
        List<Tile> tiles = new ArrayList<>();
        Tile t;
        for(int i = 0; i< 40; ++i)
        {
            for (int j=0; j< 20; ++j)
            {
                t = new NutritiousTile(new Point(i,j), Tile.TerrainTypes.PLANES, false, 1.0, 10.0);
                tiles.add(t);
            }
        }

        MasterData.map = new Map(tiles);
        MasterData.animalManager = new AnimalManager();
        MasterData.animalManager.setMap(MasterData.map);

        Animal a;
        a = new Rabbit(MasterData.map,MasterData.animalManager);
        a.setLocation(new Point(20,9));
        MasterData.animalManager.add(a);
        a = new Rabbit(MasterData.map,MasterData.animalManager);
        a.setLocation(new Point(11,9));
        a.setSpeed(20);
        a.setSensesRange(20);
        a.setReproductionDrive(20);
        MasterData.animalManager.add(a);
        a = new Rabbit(MasterData.map,MasterData.animalManager);
        a.setLocation(new Point(18,9));
        MasterData.animalManager.add(a);
        a = new Rabbit(MasterData.map,MasterData.animalManager);
        a.setLocation(new Point(0,0));
        MasterData.animalManager.add(a);
        a = new Rabbit(MasterData.map,MasterData.animalManager);
        a.setLocation(new Point(22,9));
        a.setSpeed(20);
        a.setSex("female");
        MasterData.animalManager.add(a);
        a = new Rabbit(MasterData.map,MasterData.animalManager);
        a.setLocation(new Point(11,9));
        a.setSpeed(20);
        a.setSensesRange(10);
        a.setSex("female");
        MasterData.animalManager.add(a);

        javafx.application.Application.launch(Application.class,args);

    }
}
