package com.NV.simulation.map;

import com.NV.simulation.animals.AnimalHerbivore;

import java.awt.*;

public class NutritiousTile extends Tile {

    private double nutritionContent;

    public NutritiousTile() {
        super();
        setNutritionContent(0);
    }

    public NutritiousTile(Point position, String terrainType, boolean impassible, double travelDifficulty, double nutritionContent) {
        super(position, terrainType, impassible, travelDifficulty);
        setNutritionContent(nutritionContent);
    }

    public double getNutritionContent() {
        return nutritionContent;
    }

    public void setNutritionContent(double nutritionContent) {
        if(nutritionContent>0)
        {
            if(nutritionContent<=100.0)
                this.nutritionContent = nutritionContent;
            else
                this.nutritionContent = 100.0;
        }
        else
            this.nutritionContent = 0.0;
    }
    public void addNutritionContent(double nutritionContent) {
        setNutritionContent(getNutritionContent()+nutritionContent);
    }

    public void replenishNutrition()
    {
        replenishNutrition(5.0);
    }

    public void replenishNutrition(double amount)
    {
        setNutritionContent(getNutritionContent()+amount);
    }

    public void herbivoreInteraction(AnimalHerbivore animal)
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