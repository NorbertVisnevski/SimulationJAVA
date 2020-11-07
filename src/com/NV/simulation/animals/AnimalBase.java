package com.NV.simulation.animals;

import com.NV.simulation.managers.animal.AnimalReproductionHelper;
import com.NV.simulation.managers.map.Map;
import com.NV.simulation.tile.Tile;
import com.NV.simulation.MasterData;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AnimalBase implements Animal, Serializable {

    private boolean dead = false;

    private double hunger;

    private double reproductionDrive;

    private double survivalDrive;

    private double speed;

    private double sensesRange;

    private double mutationRate;

    private Point location;

    protected List<Tile> tileOptions;


    public boolean isDead() {
        return dead;
    }

    public void setDead(){
        this.dead = true;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        if(hunger>0.0)
            this.hunger = hunger;
        else
            this.hunger = 0.0;
    }

    public double getReproductionDrive() {
        return reproductionDrive;
    }

    public void setReproductionDrive(double reproductionDrive) {
        if(reproductionDrive>1.0)
            this.reproductionDrive = reproductionDrive;
        else
            this.reproductionDrive = 1.0;
    }

    public double getSurvivalDrive() {
        return survivalDrive;
    }

    public void setSurvivalDrive(double survivalDrive) {
        if(survivalDrive>1.0)
            this.survivalDrive = survivalDrive;
        else
            this.survivalDrive = 1.0;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if(speed>1.0)
            this.speed = speed;
        else
            this.speed = 1.0;
    }

    public double getSensesRange() {
        return sensesRange;
    }

    public void setSensesRange(double sensesRange) {
        if(sensesRange>1.0)
            this.sensesRange = sensesRange;
        else
            this.sensesRange = 1.0;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        if(mutationRate>1.0)
            this.mutationRate = mutationRate;
        else
            this.mutationRate = 1.0;
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

    public AnimalBase() {
        this(0.0,1.0,1.0,1.0,1.0,1.0,new Point());
    }

    public AnimalBase(double hunger, double reproductionDrive, double survivalDrive, double speed, double sensesRange, double mutationRate, Point location) {
        setHunger(hunger);
        setReproductionDrive(reproductionDrive);
        setSurvivalDrive(survivalDrive);
        setSpeed(speed);
        setSensesRange(sensesRange);
        setMutationRate(mutationRate);
        setLocation(location);
    }

    public Point calculateMove()
    {
        tileOptions = MasterData.map.getTileNeighbours(getLocation());
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
        List<Animal> possibleMates = MasterData.animalManager.getAnimalsInRange(getLocation(), getSensesRange());

        possibleMates = possibleMates.stream().filter(animal->animal.getClass() == this.getClass()).filter(animal->animal!=this).collect(Collectors.toList());

        if(possibleMates.size()==0)
        {
            possibleMoves.add(tileOptions.get(MasterData.random.nextInt(tileOptions.size())).getPosition());
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

    public Animal mateWith(Animal other) throws Exception
    {
        return AnimalReproductionHelper.reproduce(this,other);
    }





}
