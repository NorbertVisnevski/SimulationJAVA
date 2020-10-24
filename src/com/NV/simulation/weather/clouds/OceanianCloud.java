package com.NV.simulation.weather.clouds;

import com.NV.simulation.MasterData;
import com.NV.simulation.tile.Tile;

import java.awt.*;
import java.io.Serializable;

public class OceanianCloud extends Cloud implements Serializable {
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
        this(new Point());
    }

    public OceanianCloud(Point location, double moisture) {
        super(location, moisture);
    }
}
