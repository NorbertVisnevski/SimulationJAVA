package com.NV.simulation.threads;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;

import java.sql.Time;

public class ManagerThread extends Thread {
    private int turns = 0;
    @Override
    public void run() {
        while(!isInterrupted())
        {
            System.out.println(ThreadControlFlags.simulationSpeed);
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
                turns++;
                if(true)
                {try {
                    Thread.sleep(10*ThreadControlFlags.simulationSpeed);
                }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                    Application.addCallbackFunction(()->{
                        Application.updateSimulationState();
                        Application.shuffleEntities();
                        ThreadControlFlags.frameRendered = true;
                        turns = 0;
                    });
                    ThreadControlFlags.frameRendered = false;
                }
            }
        }
    }
}
