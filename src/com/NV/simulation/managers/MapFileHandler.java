package com.NV.simulation.managers;

import com.NV.simulation.MasterData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.Future;

public class MapFileHandler implements AsyncFileHandler{
    public static File currentFile = null;

    public void writeAsync(File file)
    {
        new Thread(() -> {
            if(file!=null)
            {
                try
                {
                    System.out.println(file.getPath());
                    BufferedWriter buffer = new BufferedWriter(new FileWriter(file.getPath()));

                    buffer.writeO(MasterData.map.toString());

                    buffer.close();

                    System.out.println("File saved");
                }
                catch(Exception e){}

            }
        }).start();
    }
}
