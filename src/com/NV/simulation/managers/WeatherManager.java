package com.NV.simulation.managers;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.Map;
import com.NV.simulation.map.Tile;
import com.NV.simulation.weather.Cloud;
import com.NV.simulation.weather.ContinentalCloud;
import com.NV.simulation.weather.OceanianCloud;
import com.NV.simulation.weather.Wind;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherManager {

    private List<Cloud> cloudList = new ArrayList<>();

    private Wind wind = new Wind();

    private List<Tile> oceanList;
    private List<Tile> continentList;

    public void add(Cloud cloud)
    {
        cloudList.add(cloud);
    }

    public void add(List<Cloud> clouds)
    {
        cloudList.addAll(clouds);
    }

    public List<Cloud> getCloudList()
    {
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
        oceanList = map.getTileMap().stream().filter(tile->tile.getTerrainType().equals("WATER")||tile.getTerrainType().equals("MARSH")).collect(Collectors.toList());

        continentList = map.getTileMap().stream().filter(tile->tile.getTerrainType().equals("PLANES")||tile.getTerrainType().equals("FOREST")).collect(Collectors.toList());
    }

    public void clear()
    {
        oceanList.clear();
        continentList.clear();
        cloudList.clear();
    }

    public void updateClouds()
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
                cloud.rain(MasterData.map.getTileNeighbours(cloud.getLocation()).stream().collect(Collectors.toList()));
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
