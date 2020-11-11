package com.NV.simulation.managers.animal;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.exceptions.AnimalHybridException;

import java.lang.reflect.InvocationTargetException;

public class AnimalReproductionHelper {

    public static Animal reproduce(Animal a1, Animal a2) throws NullPointerException, AnimalHybridException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException,InstantiationException
    {
        if(a1 == null || a2 == null)
            throw new NullPointerException("2 animals are needed for reproduction");

        if(!a1.canProcreateWith(a2))
            throw new AnimalHybridException("Animals can't reproduce together",a1,a2);

        double mr1 = a1.getMutationRate();
        double mr2 = a2.getMutationRate();
        double reproductionDrive = AnimalReproductionHelper.getProperty(a1.getReproductionDrive(), a2.getReproductionDrive(), mr1, mr2);
        double speed = AnimalReproductionHelper.getProperty(a1.getSpeed(), a2.getSpeed(), mr1, mr2);
        double survivalDrive = AnimalReproductionHelper.getProperty(a1.getSurvivalDrive(), a2.getSurvivalDrive(), mr1, mr2);
        double sensesRadius = AnimalReproductionHelper.getProperty(a1.getSensesRange(), a2.getSensesRange(), mr1, mr2);
        double mutationRate = AnimalReproductionHelper.getProperty(a1.getMutationRate(), a2.getMutationRate(), mr1, mr2);

        Animal newAnimal = (Animal)Class.forName(MasterData.random.nextInt(2)==0 ? a1.getClass().getName():a2.getClass().getName()).getDeclaredConstructor().newInstance();

        newAnimal.setSpeed(speed);
        newAnimal.setLocation(a1.getLocation());
        newAnimal.setHunger(50);
        newAnimal.setReproductionDrive(reproductionDrive);
        newAnimal.setMutationRate(mutationRate);
        newAnimal.setSensesRange(sensesRadius);
        newAnimal.setSurvivalDrive(survivalDrive);

        a1.setHunger(a1.getHunger()+25.0);
        a2.setHunger(a2.getHunger()+25.0);

        return newAnimal;
    }

    public static double getProperty(double firstParentParam, double secondParentParam, double mutationRate1, double mutationRate2)
    {
        double prop = MasterData.random.nextInt()%2 == 0 ? firstParentParam : secondParentParam;
        prop += MasterData.random.nextInt()%2 == 0 ?
                (MasterData.random.nextInt()%2 == 0 ? -mutationRate1 : -mutationRate2)
                : (MasterData.random.nextInt()%2 == 0 ? mutationRate1 : mutationRate2);

        return prop;
    }

}
