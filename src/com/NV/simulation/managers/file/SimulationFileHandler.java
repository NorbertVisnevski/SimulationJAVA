package com.NV.simulation.managers.file;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.Animal;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.graphics.dialogs.ErrorDialog;
import com.NV.simulation.tile.Tile;
import com.NV.simulation.weather.Cloud;

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
                    Application.addCallbackFunction(()->{new ErrorDialog();});
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

                    MasterData.clearManagers();
                    MasterData.animalManager.add(animals);
                    MasterData.map.add(map);
                    MasterData.weatherManager.linkToMap(MasterData.map);
                    MasterData.weatherManager.add(clouds);
                    MasterData.weatherManager.setWindDirection(windDirection);
                    Application.updateSimulationState();
                });
            }
            catch(Exception e){
                System.out.println(e);
                Application.addCallbackFunction(()->{new ErrorDialog();});
            }
        });
        thread.start();
    }
}
