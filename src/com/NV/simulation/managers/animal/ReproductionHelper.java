package com.NV.simulation.managers.animal;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;

public class ReproductionHelper {

    public static Animal reproduce(Animal a1, Animal a2)
    {
        double mr1 = a1.getMutationRate();
        double mr2 = a2.getMutationRate();
        double reproductionDrive = ReproductionHelper.getProperty(a1.getReproductionDrive(), a2.getReproductionDrive(), mr1, mr2);
        double speed = ReproductionHelper.getProperty(a1.getSpeed(), a2.getSpeed(), mr1, mr2);
        double survivalDrive = ReproductionHelper.getProperty(a1.getSurvivalDrive(), a2.getSurvivalDrive(), mr1, mr2);
        double sensesRadius = ReproductionHelper.getProperty(a1.getSensesRange(), a2.getSensesRange(), mr1, mr2);
        double mutationRate = ReproductionHelper.getProperty(a1.getMutationRate(), a2.getMutationRate(), mr1, mr2);

        Animal newAnimal = null;

        try{
            newAnimal = (Animal)Class.forName(a1.getClass().getName()).getDeclaredConstructor().newInstance();
        }
        catch(Exception e){}

        if(newAnimal != null)
        {
            try{
                newAnimal.setSpeed(speed);
                newAnimal.setLocation(a1.getLocation());
                newAnimal.setHunger(50);
                newAnimal.setReproductionDrive(reproductionDrive);
                newAnimal.setMutationRate(mutationRate);
                newAnimal.setSensesRange(sensesRadius);
                newAnimal.setSurvivalDrive(survivalDrive);
            }
            catch(Exception e)
            {
                return null;
            }
        }

        a1.setHunger(a1.getHunger()+25.0);
        a2.setHunger(a2.getHunger()+25.0);

        return newAnimal;
    }

    public static double getProperty(double firstParentParam, double secondParentParam, double mutationRate1, double mutationRate2)
    {
        double prop = MasterData.random.nextInt()%2 == 0 ? firstParentParam : secondParentParam;
        prop += MasterData.random.nextInt()%2 == 0 ?
                (MasterData.random.nextInt()%2 == 0 ? -firstParentParam : -secondParentParam)
                : (MasterData.random.nextInt()%2 == 0 ? firstParentParam : secondParentParam);

        return prop;
    }

}
