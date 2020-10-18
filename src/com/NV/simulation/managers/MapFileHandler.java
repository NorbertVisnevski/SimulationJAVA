package com.NV.simulation.managers;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.map.Tile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class MapFileHandler implements AsyncFileHandler{

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

                    objectInput.close();
                    fileInput.close();

                    Application.addCallbackFunction(()->{
                        MasterData.map.clear();
                        MasterData.animalManager.clear();
                        MasterData.weatherManager.clear();
                        MasterData.map.add(map);
                        MasterData.weatherManager.linkToMap(MasterData.map);
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
