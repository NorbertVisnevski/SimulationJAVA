package com.NV.simulation.graphics;

import com.NV.simulation.animals.*;
import com.NV.simulation.MasterData;
import com.NV.simulation.map.Tile;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;


//For rendering window
//TODO fix this mess
public class Application extends javafx.application.Application {

    private final static int WINDOW_WIDTH = 2300;
    private final static int WINDOW_HEIGHT = 1100;


    public static void updateSimulationState() {
        MasterData.tileMap.getChildren().clear();
        List<Tile> tilemap = MasterData.map.getTileMap();
        for (Tile tile : tilemap) {
            MasterData.tileMap.getChildren().add(new GraphicTile(tile));
        }

        List<Animal> animalmap = MasterData.animalManager.getAnimalList();
        for (Animal animal : animalmap) {
            MasterData.tileMap.getChildren().add(new GraphicAnimal(animal));
        }
    }


    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/MainUI.fxml"));

        Parent root = loader.load();
        MasterData.mainUIController = loader.getController();
        MasterData.tileMap = MasterData.mainUIController.tilemap;

        primaryStage.setScene(new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT));
        primaryStage.show();
        primaryStage.setTitle("Simulation");
        updateSimulationState();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

            }
        }.start();

    }
}
