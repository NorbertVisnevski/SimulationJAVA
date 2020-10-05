package com.NV.simulation.weather;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.Tile;

import java.awt.*;

public class ContinentalCloud extends Cloud {

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
        this(new Point(),5.0);
    }

    public ContinentalCloud(Point location, double moisture) {
        super(location, moisture);
    }
}
