package com.NV.simulation.weather;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.Tile;

import java.awt.*;
import java.io.Serializable;

public class ContinentalCloud extends Cloud implements Serializable {

    @Override
    public void moveTo(Tile tile)
    {
        super.moveTo(tile);
        accumulateMoisture(0.05);
    }

    public ContinentalCloud(Point location) {
        this(location,5.0);
    }

    public ContinentalCloud() {
        this(new Point());
    }

    public ContinentalCloud(Point location, double moisture) {
        super(location, moisture);
    }
}
