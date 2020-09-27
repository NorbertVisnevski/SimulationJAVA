package com.simulation.map;

import com.simulation.animals.Herbivore;
import com.simulation.map.Tile;

import java.awt.*;

public class NutritiousTile extends Tile {

    private double nutritionContent;

    public NutritiousTile() {
        super();
        this.nutritionContent = 0.0;
    }

    public NutritiousTile(Point position, String terrainType, boolean impassible, double travelDifficulty, double nutritionContent) {
        super(position, terrainType, impassible, travelDifficulty);
        this.nutritionContent = nutritionContent;
    }

    public double getNutritionContent() {
        return nutritionContent;
    }

    public void setNutritionContent(double nutritionContent) {
        if(nutritionContent>0)
            this.nutritionContent = nutritionContent;
        else
            this.nutritionContent = 0.0;
    }

    public void replenishNutrition()
    {
        replenishNutrition(5.0);
    }

    public void replenishNutrition(double amount)
    {
        setNutritionContent(getNutritionContent()+amount);
    }

    public void herbivoreInteraction(Herbivore animal)
    {
        if(getNutritionContent()<animal.getHunger())
        {
            animal.setHunger(animal.getHunger()-getNutritionContent());
            setNutritionContent(0.0);
        }
        else
        {
            setNutritionContent(getNutritionContent()-animal.getHunger());
            animal.setHunger(0.0);
        }
    }

}
