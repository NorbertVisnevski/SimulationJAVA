package com.NV.simulation.weather.wind;

import com.NV.simulation.MasterData;

import java.util.ArrayList;
import java.util.List;

public class Wind {

    private String direction;

    private static final List<String> directions;

    static
    {
        directions = new ArrayList<>();
        directions.add(WindDirections.EAST);
        directions.add(WindDirections.WEST);
        directions.add(WindDirections.NORTH_EAST);
        directions.add(WindDirections.NORTH_WEST);
        directions.add(WindDirections.SOUTH_EAST);
        directions.add(WindDirections.SOUTH_WEST);
    }

    public static class WindDirections
    {
        public static final String EAST = "EAST";
        public static final String WEST = "WEST";
        public static final String NORTH = "NORTH";
        public static final String SOUTH = "SOUTH";
        public static final String NORTH_EAST = "NORTH_EAST";
        public static final String NORTH_WEST = "NORTH_WEST";
        public static final String SOUTH_EAST = "SOUTH_EAST";
        public static final String SOUTH_WEST = "SOUTH_WEST";
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void updateWind()
    {
        setDirection(directions.get(MasterData.random.nextInt(directions.size())));
    }

    public Wind() {
        updateWind();
    }
}
