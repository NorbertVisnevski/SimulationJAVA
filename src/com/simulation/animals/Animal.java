package com.simulation.animals;

import com.simulation.managers.AnimalManager;
import com.simulation.map.Map;
import com.simulation.map.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Animal {

    private double hunger;

    private double reproductionDrive;

    private double survivalDrive;

    private String sex;

    private double speed;

    private double sensesRange;

    private double mutationRate;

    private Point location;

    protected List<Tile> tileOptions;

    protected Map map;

    protected AnimalManager animalManager;

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        if(hunger>0)
            this.hunger = hunger;
        else
            this.hunger = 0.0;
    }

    public double getReproductionDrive() {
        return reproductionDrive;
    }

    public void setReproductionDrive(double reproductionDrive) {
        this.reproductionDrive = reproductionDrive;
    }

    public double getSurvivalDrive() {
        return survivalDrive;
    }

    public void setSurvivalDrive(double survivalDrive) {
        this.survivalDrive = survivalDrive;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSensesRange() {
        return sensesRange;
    }

    public void setSensesRange(double sensesRange) {
        this.sensesRange = sensesRange;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public double getNutritionExpenses()
    {
        return speed/2.0;
    }

    public Animal(Map map, AnimalManager animalManager) {
        this(map,animalManager,1.0,1.0,1.0,"male",1.0,1.0,1.0,new Point());
    }

    public Animal( Map map, AnimalManager animalManager, double hunger, double reproductionDrive, double survivalDrive, String sex, double speed, double sensesRange, double mutationRate, Point location) {
        this.hunger = hunger;
        this.reproductionDrive = reproductionDrive;
        this.survivalDrive = survivalDrive;
        this.sex = sex;
        this.speed = speed;
        this.sensesRange = sensesRange;
        this.mutationRate = mutationRate;
        this.location = location;
        this.map = map;
        this.animalManager = animalManager;
    }

    public Point calculateMove()
    {
        tileOptions = map.getTileNeighbours(getLocation(),true);
        List<Point> possibleMoves = new ArrayList<>();
        findFood(possibleMoves);
        findMate(possibleMoves);
        survive(possibleMoves);

        double x = 0;
        double y = 0;
        double divider = getHunger() + getReproductionDrive() + getSurvivalDrive();
        x += possibleMoves.get(0).x * getHunger();
        x += possibleMoves.get(0).x * getReproductionDrive();
        x += possibleMoves.get(0).x * getSurvivalDrive();

        y += possibleMoves.get(0).y * getHunger();
        y += possibleMoves.get(0).y * getReproductionDrive();
        y += possibleMoves.get(0).y * getSurvivalDrive();

        return new Point((int)(x/divider), (int)(y/divider));
    }

    protected abstract void findFood(List<Point> possibleMoves);

    protected void findMate(List<Point> possibleMoves)
    {
        List<Animal> possibleMates = animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        possibleMates = possibleMates.stream().filter(animal->animal.getClass() == this.getClass()).collect(Collectors.toList());

        if(getSex().equals("male"))
            possibleMates = possibleMates.stream().filter(animal->animal.getSex().equals("female")).collect(Collectors.toList());
        else if(getSex().equals("female"))
            possibleMates = possibleMates.stream().filter(animal->animal.getSex().equals("male")).collect(Collectors.toList());

        if(possibleMates.size()==0)
        {
            Random rand = new Random();
            possibleMoves.add(tileOptions.get(rand.nextInt(tileOptions.size())).getPosition());
            return;
        }

        List<Point> pathToMate = new ArrayList<>();
        for(Animal anim : possibleMates)
        {
            pathToMate.add(Map.findClosest(tileOptions, anim.getLocation()));
        }
        int x = 0;
        int y = 0;

        for (Point p : pathToMate)
        {
            x+=p.x;
            y+=p.y;
        }

        possibleMoves.add( new Point(x/pathToMate.size(), y/pathToMate.size()));
    }

    protected abstract void survive(List<Point> possibleMoves);

    public abstract Animal mateWith(Animal other);




}
