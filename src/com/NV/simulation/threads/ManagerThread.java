package com.NV.simulation.threads;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;

import java.sql.Time;

public class ManagerThread extends Thread {

    @Override
    public void run() {
        while(!isInterrupted())
        {
            if(ThreadControlFlags.frameRendered)
            {
                Thread animal = new Thread(()-> {
                    MasterData.animalManager.update();
                });
                Thread map = new Thread(()-> {
                    MasterData.map.update();
                });
                Thread weather = new Thread(()-> {
                    MasterData.weatherManager.update();
                });
                animal.start();
                map.start();
                weather.start();
                try{
                    animal.join();
                    map.join();
                    weather.join();
                }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                try {
                    Thread.sleep(10*ThreadControlFlags.simulationSpeed);
                }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                    Application.addCallbackFunction(()->{
                        Application.updateSimulationState();
                        Application.shuffleEntities();
                        ThreadControlFlags.frameRendered = true;
                    });
                    ThreadControlFlags.frameRendered = false;
            }
        }
    }
}
