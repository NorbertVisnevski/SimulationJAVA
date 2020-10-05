package com.NV.simulation.weather;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.NutritiousTile;
import com.NV.simulation.map.Tile;

import java.awt.*;
import java.util.List;

public abstract class Cloud {

    private Point location;

    private double moisture;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        if(moisture > 0)
            this.moisture = moisture;
        else
            this.moisture = 0;
    }

    public void accumulateMoisture()
    {
        this.accumulateMoisture(1);
    }

    public void accumulateMoisture(double amount)
    {
        setMoisture(getMoisture()+amount);
    }

    public Cloud() {
        this(new Point(0,0),0);
    }

    public Cloud(Point location, double moisture) {
        setLocation(location);
        setMoisture(moisture);
    }

    public void moveTo(Tile tile)
    {
        setLocation(tile.getPosition());
    }

    public boolean willRain()
    {
        if(MasterData.random.nextInt(10)==0)
            return true;
        return false;
    }

    public void rain(List<Tile>list)
    {
        list.stream().filter(tile->tile.getClass() == NutritiousTile.class).forEach(tile->((NutritiousTile)tile).addNutritionContent(getMoisture()/ list.size()));
    }

}
