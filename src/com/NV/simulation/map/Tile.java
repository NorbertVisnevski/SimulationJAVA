package com.NV.simulation.map;

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




    public Tile() {
        this(new Point(), Tile.TerrainTypes.WATER, true, 0);
    }

    public Tile(Point position, String terrainType, boolean impassible, double travelDifficulty) {
        this.position = position;
        this.terrainType = terrainType;
        this.impassible = impassible;
        this.travelDifficulty = travelDifficulty;
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
}
