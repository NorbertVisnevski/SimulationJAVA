package com.NV.simulation.managers.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ErrorLogger implements AsyncLogHandler{
    @Override
    public void append(File file, String message) {
        new Thread(()->{
            try(FileWriter writer = new FileWriter(file.getName()))
            {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                writer.append("\n");
                writer.append(formatter.format(new Date()));
                writer.append(" : ");
                writer.append(message);
                writer.append("\n");

            }
            catch(IOException e) { }
        }).start();
    }

    @Override
    public void clear(File file) {
        new Thread(()->{
            try(FileWriter writer = new FileWriter(file.getName()))
            {
                writer.write("");
            }
            catch(IOException e) { }
        }).start();
    }
}
