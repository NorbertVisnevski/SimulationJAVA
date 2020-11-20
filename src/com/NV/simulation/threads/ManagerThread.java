package com.NV.simulation.threads;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;

import java.sql.Date;
import java.sql.Time;

public class ManagerThread extends Thread {
    private volatile int turns = 0;
    @Override
    public void run() {
        while(!isInterrupted())
        {
            if(ThreadControlFlags.getInstance().frameRendered)
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
                if(turns >= (ThreadControlFlags.getInstance().simulationSpeed-100)/-100.0*100.0)
                {
                    try {
                        Thread.sleep(10*ThreadControlFlags.getInstance().simulationSpeed);
                    }
                    catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                    Application.addCallbackFunction(()->{
                        Application.updateSimulationState();
                        Application.shuffleEntities();
                        ThreadControlFlags.getInstance().frameRendered = true;
                        turns=0;
                    });
                    ThreadControlFlags.getInstance().frameRendered = false;
                }
            }
        }
    }
}
