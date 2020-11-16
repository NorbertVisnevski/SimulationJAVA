package com.NV.simulation.threads;

import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.Application;

public class ManagerThread extends Thread {
    @Override
    public void run() {
        while(!isInterrupted())
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
            Application.addCallbackFunction(()->{
                Application.updateSimulationState();
                Application.shuffleEntities();
            });
            System.out.println("Thread runniing");

        }
        System.out.println("Thread stoped");
    }
}
