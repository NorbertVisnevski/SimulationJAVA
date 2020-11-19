package com.NV.simulation.managers.file;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.Animal;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.graphics.dialogs.ErrorDialog;
import com.NV.simulation.tile.Tile;
import com.NV.simulation.weather.clouds.Cloud;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationFileHandler implements AsyncFileHandler{

    public void writeAsync(File file)
    {
        if(file != null)
        {
            new Thread(() -> {
                try( FileOutputStream fileOutput = new FileOutputStream(file.getPath());
                     ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput))
                {
                    objectOutput.writeObject(MasterData.map.getList());
                    objectOutput.writeObject(MasterData.animalManager.getList());
                    objectOutput.writeObject(MasterData.weatherManager.getList());
                    objectOutput.writeObject(MasterData.weatherManager.getWindDirection());
                    MasterData.currentFile = file;
                }
                catch(IOException e)
                {
                    Application.addCallbackFunction(()->{new ErrorDialog("File Error","Input output OS failure");});
                }
                catch(Exception e)
                {
                    Application.addCallbackFunction(()->{new ErrorDialog("File Error","Error: unknown");});
                }
            }).start();
        }
    }

    public void readAsync(File file)
    {
        Thread thread = new Thread(()-> {
            try(FileInputStream fileInput = new FileInputStream(file.getPath());
                ObjectInputStream objectInput = new ObjectInputStream(fileInput))
            {

                List<Tile> map = (ArrayList<Tile>) objectInput.readObject();
                List<Animal> animals = (ArrayList<Animal>)objectInput.readObject();
                List<Cloud> clouds = (ArrayList<Cloud>)objectInput.readObject();
                String windDirection = (String)objectInput.readObject();

                Application.addCallbackFunction(()->{

                    MasterData.clearManagers();
                    MasterData.animalManager.add(animals);
                    MasterData.map.add(map);
                    MasterData.weatherManager.linkToMap(MasterData.map);
                    MasterData.weatherManager.add(clouds);
                    MasterData.weatherManager.setWindDirection(windDirection);

                    MasterData.currentFile = file;

                    Application.cleanUpdateSimulationState();
                    Application.shuffleEntities();
                });
            }
            catch(StreamCorruptedException e)
            {
                Application.addCallbackFunction(()->{new ErrorDialog("File Error","File got corrupted or was saved with a outdated version of this application");});
            }
            catch(IOException e)
            {
                Application.addCallbackFunction(()->{new ErrorDialog("File Error","Input output OS failure");});
            }
            catch(ClassNotFoundException e)
            {
                Application.addCallbackFunction(()->{new ErrorDialog("File Error","Unable to understand saved content");});
            }
            catch(Exception e)
            {
                Application.addCallbackFunction(()->{new ErrorDialog("File Error","Error: unknown");});
            }
        });
        thread.start();
    }
}
