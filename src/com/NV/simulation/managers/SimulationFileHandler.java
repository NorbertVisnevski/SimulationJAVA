package com.NV.simulation.managers;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.Animal;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.map.Map;
import com.NV.simulation.map.Tile;
import com.NV.simulation.weather.Cloud;
import com.NV.simulation.weather.Wind;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationFileHandler implements AsyncFileHandler{

    public void writeAsync(File file)
    {
        if(file != null)
        {
            new Thread(() -> {
                try
                {
                    System.out.println(file.getPath());
                    FileOutputStream fileOutput = new FileOutputStream(file.getPath());
                    ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

                    // Method for serialization of object
                    objectOutput.writeObject(MasterData.map.getTileMap());
                    objectOutput.writeObject(MasterData.animalManager.getAnimalList());
                    objectOutput.writeObject(MasterData.weatherManager.getCloudList());
                    objectOutput.writeObject(MasterData.weatherManager.getWindDirection());

                    objectOutput.close();
                    fileOutput.close();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }).start();
        }
    }

    public void readAsync(File file)
    {
        Thread thread = new Thread(()-> {
            try
            {
                System.out.println(file.getPath());
                FileInputStream fileInput = new FileInputStream(file.getPath());
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);

                List<Tile> map = (ArrayList<Tile>) objectInput.readObject();
                List<Animal> animals = (ArrayList<Animal>)objectInput.readObject();
                List<Cloud> clouds = (ArrayList<Cloud>)objectInput.readObject();
                String windDirection = (String)objectInput.readObject();

                objectInput.close();
                fileInput.close();

                Application.addCallbackFunction(()->{

                    MasterData.animalManager.clear();
                    MasterData.animalManager.add(animals);
                    MasterData.map.clear();
                    MasterData.map.add(map);
                    MasterData.weatherManager.clear();
                    MasterData.weatherManager.linkToMap(MasterData.map);
                    MasterData.weatherManager.add(clouds);
                    MasterData.weatherManager.setWindDirection(windDirection);
                    Application.updateSimulationState();
                });
            }
            catch(Exception e){
                System.out.println(e);
            }
        });
        thread.start();
    }
}
