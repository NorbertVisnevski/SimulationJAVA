package com.NV.simulation.graphics.entities;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import com.NV.simulation.graphics.GraphicSettings;
import com.NV.simulation.graphics.TextureStorage;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GraphicAnimal extends Rectangle implements WithStatTooltip {

    private Tooltip toolTip = new Tooltip();

    private Animal animal;

    private double x;
    private double y;

    public GraphicAnimal(Animal animal) {
        this.animal = animal;
        double x = animal.getLocation().x * GraphicSettings.TILE_WIDTH + (animal.getLocation().y % 2) * GraphicSettings.n + GraphicSettings.xStartOffset;
        double y = animal.getLocation().y * GraphicSettings.TILE_HEIGHT * 0.75 + GraphicSettings.yStartOffset;
        setX(x);
        this.x=x;
        this.y=y;
        setY(y);
        setHeight(32);
        setWidth(32);
        initTooltip();
        updateTooltip();
    }

    public void shufflePosition()
    {
        setX(x+MasterData.random.nextInt(32));
        setY(y-32+MasterData.random.nextInt(48));

    }

    @Override
    public void initTooltip() {
        Tooltip.install(this,toolTip);
    }

    @Override
    public void updateTooltip() {
        MasterData.stringBuilder.setLength(0);
        MasterData.stringBuilder.append("Animal\n");

        if(animal instanceof AnimalHerbivore)
        {
            MasterData.stringBuilder.append("Type: Herbivore\n");
        }
        else if(animal instanceof AnimalCarnivore)
        {
            MasterData.stringBuilder.append("Type: Carnivore\n");
        }
        if(animal.getClass() == AnimalRabbit.class)
        {
            setFill(new ImagePattern(TextureStorage.rabbit));
            MasterData.stringBuilder.append("Species: Rabbit\n");
        }
        else if(animal.getClass() == AnimalDeer.class)
        {
            setFill(new ImagePattern(TextureStorage.deer));
            MasterData.stringBuilder.append("Species: Deer\n");
        }
        else if(animal.getClass() == AnimalWolf.class)
        {
            setFill(new ImagePattern(TextureStorage.wolf));
            MasterData.stringBuilder.append("Species: Wolf\n");
        }
        else if(animal.getClass() == AnimalFox.class)
        {
            setFill(new ImagePattern(TextureStorage.fox));
            MasterData.stringBuilder.append("Species: Fox\n");
        }
        else
        {
            setFill(Color.SALMON);
            MasterData.stringBuilder.append("Species: not available\n");
        }

        MasterData.stringBuilder.append("Hunger: ");
        MasterData.stringBuilder.append(String.format("%.02f",animal.getHunger()));
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Speed: ");
        MasterData.stringBuilder.append(String.format("%.02f",animal.getSpeed()));
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Energy expenses: ");
        MasterData.stringBuilder.append(String.format("%.02f",animal.getNutritionExpenses()));
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Sensing range: ");
        MasterData.stringBuilder.append(String.format("%.02f",animal.getSensesRange()));
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Reproduction drive: ");
        MasterData.stringBuilder.append(String.format("%.02f",animal.getReproductionDrive()));
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Survival drive: ");
        MasterData.stringBuilder.append(String.format("%.02f",animal.getSurvivalDrive()));
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Mutation rate: ");
        MasterData.stringBuilder.append(String.format("%.02f",animal.getMutationRate()));
        MasterData.stringBuilder.append("\n");


        toolTip.setText(MasterData.stringBuilder.toString());
    }

    @Override
    public void clearTooltip() {
        toolTip.setText("");
    }

    @Override
    public void uninstallTooltip() {
        Tooltip.uninstall(this,toolTip);
    }
}
