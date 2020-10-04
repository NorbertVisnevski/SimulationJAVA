package com.NV.simulation.graphics;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.*;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class GraphicAnimal extends Rectangle {
    GraphicAnimal(Animal animal) {
        double x = animal.getLocation().x * GraphicSettings.TILE_WIDTH + (animal.getLocation().y % 2) * GraphicSettings.n + GraphicSettings.xStartOffset;
        double y = animal.getLocation().y * GraphicSettings.TILE_HEIGHT * 0.75 + GraphicSettings.yStartOffset;
        setX(x + MasterData.random.nextInt(48));
        setY(y + MasterData.random.nextInt(16));
        setHeight(8);
        setWidth(8);

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
            setFill(Color.WHITE);
            MasterData.stringBuilder.append("Species: Rabbit\n");
        }
        else if(animal.getClass() == AnimalWolf.class)
        {
            setFill(Color.GREY);
            MasterData.stringBuilder.append("Species: Wolf\n");
        }
        else if(animal.getClass() == AnimalFox.class)
        {
            setFill(Color.ORANGE);
            MasterData.stringBuilder.append("Species: Fox\n");
        }
        else
        {
            setFill(Color.SALMON);
            MasterData.stringBuilder.append("Species: not available\n");
        }

        MasterData.stringBuilder.append("Hunger: ");
        MasterData.stringBuilder.append(animal.getHunger());
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Speed: ");
        MasterData.stringBuilder.append(animal.getSpeed());
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Energy expenses: ");
        MasterData.stringBuilder.append(animal.getNutritionExpenses());
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Sensing range: ");
        MasterData.stringBuilder.append(animal.getSensesRange());
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Reproduction drive: ");
        MasterData.stringBuilder.append(animal.getReproductionDrive());
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Survival drive: ");
        MasterData.stringBuilder.append(animal.getSurvivalDrive());
        MasterData.stringBuilder.append("\n");

        MasterData.stringBuilder.append("Mutation rate: ");
        MasterData.stringBuilder.append(animal.getMutationRate());
        MasterData.stringBuilder.append("\n");

        Tooltip.install(this, new Tooltip(MasterData.stringBuilder.toString()));

    }
}
