package com.NV.simulation.map;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Map {

    private HashMap<Point,Tile> tileMap;

    public Map() {
        this.tileMap = new HashMap<>();
    }

    public List<Tile> getTileMap() {
        return new ArrayList<>(tileMap.values().stream().collect(Collectors.toList()));
    }

    public void add(Tile tile)
    {
        tileMap.put(tile.getPosition(),tile);
    }
    public void add(List<Tile> list)
    {
        for (Tile tile:list) {
            tileMap.put(tile.getPosition(),tile);
        }
    }
    public List<Tile> getTileNeighbours(Point position)
    {
        return getTileNeighbours(position,false);
    }
    public List<Tile> getTileNeighbours(Point position, boolean canWalkOn)
    {
         List<Tile> list = new ArrayList<>();
         list.add(getTileAt(new Point(position)));
         if(position.y % 2 == 0)
         {
             list.add(getTileAt(new Point(position.x - 1, position.y - 1)));
             list.add(getTileAt(new Point(position.x, position.y - 1)));
             list.add(getTileAt(new Point(position.x + 1, position.y)));
             list.add(getTileAt(new Point(position.x, position.y + 1)));
             list.add(getTileAt(new Point(position.x - 1, position.y + 1)));
             list.add(getTileAt(new Point(position.x - 1, position.y)));
         }
         else
         {
             list.add(getTileAt(new Point(position.x - 1, position.y)));
             list.add(getTileAt(new Point(position.x, position.y - 1)));
             list.add(getTileAt(new Point(position.x + 1, position.y - 1)));
             list.add(getTileAt(new Point(position.x + 1, position.y)));
             list.add(getTileAt(new Point(position.x + 1, position.y + 1)));
             list.add(getTileAt(new Point(position.x, position.y+1)));
         }
         list = list.stream().filter(tile->tile!=null).collect(Collectors.toList());
         if(canWalkOn)
         {
             list = list.stream().filter(tile->!tile.isImpassible()).collect(Collectors.toList());
         }
         return list;
    }

    public static Point findClosest(List<Tile> tileMap, Point point)
    {
        Comparator<Tile> cmp = new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                Double a = Math.floor(o1.getPosition().distance(point));
                Double b = Math.floor(o2.getPosition().distance(point));
                return a.compareTo(b);
            }
        };
        List<Point> possibleMoves = new ArrayList<>();
        possibleMoves.add(tileMap.stream().min((t1,t2)->cmp.compare(t1,t2)).get().getPosition());

        int x = 0;
        int y = 0;
        for (Point p : possibleMoves)
        {
            x+=p.x;
            y+=p.y;
        }

        return new Point(x/possibleMoves.size(), y/possibleMoves.size());
    }

    public static Point findFurthest(List<Tile> tileMap, Point point)
    {
        Comparator<Tile> cmp = new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                Double a = Math.floor(o1.getPosition().distance(point));
                Double b = Math.floor(o2.getPosition().distance(point));
                return a.compareTo(b);
            }
        };
        List<Point> possibleMoves = new ArrayList<>();
        possibleMoves.add(tileMap.stream().max((t1,t2)->cmp.compare(t1,t2)).get().getPosition());

        int x = 0;
        int y = 0;
        for (Point p : possibleMoves)
        {
            x+=p.x;
            y+=p.y;
        }

        return new Point(x/possibleMoves.size(), y/possibleMoves.size());

    }

    public Tile getTileAt(Point coordinates)
    {
        return tileMap.get(coordinates);
    }

    public void replenishGroundNutrience(double amount)
    {
        getTileMap().stream().forEach(tile->tile.setNutritionContent(tile.getNutritionContent()+amount));
    }
}
