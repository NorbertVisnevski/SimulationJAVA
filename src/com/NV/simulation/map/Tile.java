package com.NV.simulation.map;

import com.NV.simulation.animals.AnimalHerbivore;

import java.awt.*;

public class Tile {

    private Point position;

    private String terrainType;

    public static final class TerrainTypes
    {
        public static final String MOUNTAINS = "MOUNTAINS";
        public static final String PLANES = "PLANES";
        public static final String MARSH = "MARSH";
        public static final String FOREST = "FOREST";
        public static final String WATER = "WATER";
        public static final String DESERT = "DESERT";
        public static final String FROZEN_WASTELAND = "FROZEN_WASTELAND";
    }

    private boolean impassible;

    private double travelDifficulty;

    private double nutritionContent;


    public Tile() {
        this(new Point(), Tile.TerrainTypes.WATER, true, 0,0);
    }

    public Tile(Point position, String terrainType, boolean impassible, double travelDifficulty, double nutritionContent) {
        setPosition(position);
        setTerrainType(terrainType);
        setImpassible(impassible);
        setTravelDifficulty(travelDifficulty);
        setNutritionContent(nutritionContent);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
    }

    public boolean isImpassible() {
        return impassible;
    }

    public void setImpassible(boolean impassible) {
        this.impassible = impassible;
    }

    public double getTravelDifficulty() {
        return travelDifficulty;
    }

    public void setTravelDifficulty(double travelDifficulty) {
        this.travelDifficulty = travelDifficulty;
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
        replenishNutrition(0.01);
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
