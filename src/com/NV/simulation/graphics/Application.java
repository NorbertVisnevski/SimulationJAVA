package com.NV.simulation.graphics;

import com.NV.simulation.animals.*;
import com.NV.simulation.MasterData;
import com.NV.simulation.controllers.WindController;
import com.NV.simulation.map.Tile;
import com.NV.simulation.weather.Cloud;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


//For rendering window
//TODO fix this mess
public final class Application extends javafx.application.Application {

    private final static int WINDOW_WIDTH = 2300;
    private final static int WINDOW_HEIGHT = 1100;

    private static Group animalGroup = new Group();
    private static Group tileGroup = new Group();
    private static Group cloudGroup = new Group();


    private static Deque<Runnable> callbackList = new LinkedList<>();

    public synchronized static void addCallbackFunction(Runnable runnable)
    {
        callbackList.addLast(runnable);
    }

    public static void updateSimulationState() {
        animalGroup.getChildren().clear();
        tileGroup.getChildren().clear();
        cloudGroup.getChildren().clear();
        List<Tile> tilemap = MasterData.map.getTileMap();
        for (Tile tile : tilemap) {
            tileGroup.getChildren().add(new GraphicTile(tile));
        }

        List<Animal> animalmap = MasterData.animalManager.getAnimalList();
        for (Animal animal : animalmap) {
            animalGroup.getChildren().add(new GraphicAnimal(animal));
        }

        List<Cloud> cloudList = MasterData.weatherManager.getCloudList();
        for (Cloud cloud : cloudList) {
            cloudGroup.getChildren().add(new GraphicalCloud(cloud));
        }
    }


    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/MainUI.fxml"));

        Parent root = loader.load();
        MasterData.mainUIController = loader.getController();
        MasterData.tileMap = MasterData.mainUIController.tilemap;
        MasterData.tileMap.getChildren().addAll(tileGroup,animalGroup,cloudGroup);

        VBox actionBar = MasterData.mainUIController.actionBar;

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/TileEditor.fxml"));
        actionBar.getChildren().add(loader.load());
        MasterData.tileEditController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/WindIndicator.fxml"));
        actionBar.getChildren().add(loader.load());

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/SimpleActions.fxml"));
        actionBar.getChildren().add(loader.load());

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/UI/AddAnimal.fxml"));
        actionBar.getChildren().add(loader.load());
        MasterData.addAnimalController = loader.getController();


        MasterData.mainWindow = MasterData.mainUIController.tilemap.getScene().getWindow();

        primaryStage.setTitle("Simulation");
        updateSimulationState();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if(callbackList.size()>0)
                {
                    callbackList.getFirst().run();
                    callbackList.removeFirst();
                }
            }
        }.start();

    }
}
