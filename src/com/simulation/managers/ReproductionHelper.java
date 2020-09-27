package com.simulation.managers;

import java.util.Random;

public class ReproductionHelper {

    private static Random rnd = new Random();

    public static double getProperty(double firstParentParam, double secondParentParam, double mutationRate1, double mutationRate2)
    {
        double prop = rnd.nextInt()%2 == 0 ? firstParentParam : secondParentParam;
        prop += rnd.nextInt()%2 == 0 ?
                (rnd.nextInt()%2 == 0 ? -firstParentParam : -secondParentParam)
                : (rnd.nextInt()%2 == 0 ? firstParentParam : secondParentParam);

        return prop;
    }
    public static String getSex()
    {
        return rnd.nextInt()%2 == 0 ? "male" : "female";
    }
}
