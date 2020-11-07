package com.NV.simulation.managers.weather;

import com.NV.simulation.MasterData;
import com.NV.simulation.managers.CollectionManager;
import com.NV.simulation.managers.map.Map;
import com.NV.simulation.tile.Tile;
import com.NV.simulation.weather.clouds.Cloud;
import com.NV.simulation.weather.clouds.ContinentalCloud;
import com.NV.simulation.weather.clouds.OceanianCloud;
import com.NV.simulation.weather.wind.Wind;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherManager implements CollectionManager<Cloud> {

    private final List<Cloud> cloudList = new ArrayList<>();

    private final Wind wind = new Wind();

    private List<Tile> oceanList;
    private List<Tile> continentList;

    public void add(Cloud cloud)
    {
        if(cloud!=null)
        cloudList.add(cloud);
    }

    public void add(Collection<Cloud> clouds)
    {
        cloudList.addAll(clouds.stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }

    public List<Cloud> getList() {
        return new ArrayList<>(cloudList);
    }

    public void generateNewClouds()
    {
        if(continentList.size() > 0 && MasterData.random.nextInt(2)==0)
        {
            cloudList.add(new ContinentalCloud(continentList.get(MasterData.random.nextInt(continentList.size())).getPosition()));
        }
        if(oceanList.size() > 0 && MasterData.random.nextInt(2)==0)
        {
            cloudList.add(new OceanianCloud(oceanList.get(MasterData.random.nextInt(oceanList.size())).getPosition()));
        }
    }

    public void linkToMap(Map map)
    {
        oceanList = map.getList().stream().filter(tile->tile.getTerrainType().equals("WATER")||tile.getTerrainType().equals("MARSH")).collect(Collectors.toList());

        continentList = map.getList().stream().filter(tile->tile.getTerrainType().equals("PLANES")||tile.getTerrainType().equals("FOREST")).collect(Collectors.toList());
    }

    public void clear()
    {
        oceanList.clear();
        continentList.clear();
        cloudList.clear();
    }

    public void update()
    {
        Iterator<Cloud> i = cloudList.iterator();
        while (i.hasNext())
        {
            Cloud cloud = i.next();
            Point newPos = new Point(cloud.getLocation());
            switch(wind.getDirection())
            {
                case Wind.WindDirections.EAST:
                    newPos.x= newPos.x+1;
                    break;
                case Wind.WindDirections.WEST:
                    newPos.x = newPos.x-1;
                    break;
                case Wind.WindDirections.NORTH_EAST:
                    if(newPos.y%2!=0)
                    {
                        newPos.x = newPos.x+1;
                    }
                    newPos.y = newPos.y-1;
                    break;
                case Wind.WindDirections.NORTH_WEST:
                    if(newPos.y%2==0)
                    {
                        newPos.x = newPos.x-1;
                    }
                    newPos.y = newPos.y-1;
                    break;
                case Wind.WindDirections.SOUTH_EAST:
                    if(newPos.y%2!=0)
                    {
                        newPos.x = newPos.x+1;
                    }
                    newPos.y = newPos.y+1;
                    break;
                case Wind.WindDirections.SOUTH_WEST:
                    if(newPos.y%2==0)
                    {
                        newPos.x = newPos.x-1;
                    }
                    newPos.y = newPos.y+1;
                    break;
            }

            Tile tile = MasterData.map.getTileAt(newPos);
            if(tile == null)
            {
                i.remove();
                continue;
            }
            cloud.moveTo(tile);
            if(cloud.willRain())
            {
                cloud.rain(new ArrayList<>(MasterData.map.getTileNeighbours(cloud.getLocation())));
                i.remove();
                continue;
            }
        }
        wind.updateWind();
        generateNewClouds();
    }

    public String getWindDirection()
    {
        return wind.getDirection();
    }

    public void setWindDirection(String direction)
    {
       wind.setDirection(direction);
    }
}
