package com.simulation.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Map {

    private List<Tile> tileMap;

    public Map(List<Tile> tileMap) {
        this.tileMap = tileMap;
    }

    public List<Tile> getTileMap() {
        return tileMap;
    }

    public Map getTileMapInArea(Point point, double radius, boolean walkable) {
        List<Tile> radiusMap = new ArrayList<>(
                getTileMap()
                .stream()
                .filter(tile->{
                    if (Math.floor(tile.getPosition().distance(point)) <= radius) {
                        if(walkable) {
                            if(!tile.isImpassible()) {
                                return true;
                            }
                            return false;
                        }
                            return true;
                    }
                    return false;
                })
                .collect(Collectors.toList()));
        return new Map(radiusMap);
    }

    public List<Tile> getTileNeighbours(Point position, boolean canWalkOn)
    {
         List<Tile> list = new ArrayList<>(
                getTileMap()
                        .stream()
                        .filter(tile-> {
                                if(tile.getPosition().equals(position)) {
                                    return true;
                                }
                                else if(tile.getPosition().y % 2 == 0) {//java 15 looks like has this reversed
                                    return (tile.getPosition().x == (position.x) && tile.getPosition().y == (position.y - 1)) ||
                                        (tile.getPosition().x == (position.x + 1) && tile.getPosition().y == (position.y - 1)) ||
                                        (tile.getPosition().x == (position.x + 1) && tile.getPosition().y == (position.y)) ||
                                        (tile.getPosition().x == (position.x + 1) && tile.getPosition().y == (position.y + 1)) ||
                                        (tile.getPosition().x == (position.x) && tile.getPosition().y == (position.y + 1)) ||
                                        (tile.getPosition().x == (position.x - 1) && tile.getPosition().y == (position.y));
                                }
                                else {
                                    return(tile.getPosition().x == (position.x - 1) && tile.getPosition().y == (position.y - 1)) ||
                                            (tile.getPosition().x == (position.x) && tile.getPosition().y == (position.y - 1)) ||
                                            (tile.getPosition().x == (position.x + 1) && tile.getPosition().y == (position.y)) ||
                                            (tile.getPosition().x == (position.x) && tile.getPosition().y == (position.y + 1)) ||
                                            (tile.getPosition().x == (position.x - 1) && tile.getPosition().y == (position.y + 1)) ||
                                            (tile.getPosition().x == (position.x - 1) && tile.getPosition().y == (position.y));
                                }
                        })
                        .limit(7)
                        .collect(Collectors.toList()));
         if(canWalkOn)
         {
             list.stream().filter(tile->!tile.isImpassible()).collect(Collectors.toList());
         }
         return list;
    }

    public void setTileMap(List<Tile> tileMap) {
        this.tileMap = tileMap;
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
        return tileMap.stream().filter(tile->tile.getPosition().equals(coordinates)).findFirst().get();
    }
}
