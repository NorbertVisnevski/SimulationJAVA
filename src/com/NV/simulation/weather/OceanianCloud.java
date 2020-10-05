package com.NV.simulation.weather;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.Tile;

import java.awt.*;

public class OceanianCloud extends Cloud {
    @Override
    public void moveTo(Tile tile)
    {
        if(!tile.getTerrainType().equals(Tile.TerrainTypes.MOUNTAINS))
            super.moveTo(tile);
        accumulateMoisture(0.5);
    }

    @Override
    public boolean willRain()
    {
        if(MasterData.random.nextInt(5)==0)
            return true;
        return false;
    }

    public OceanianCloud(Point location) {
        this(location,25.0);
    }

    public OceanianCloud() {
        this(new Point(),25.0);
    }

    public OceanianCloud(Point location, double moisture) {
        super(location, moisture);
    }
}