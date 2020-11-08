package com.NV.simulation.graphics;

import com.NV.simulation.animals.*;
import com.NV.simulation.MasterData;
import com.NV.simulation.graphics.entities.GraphicAnimal;
import com.NV.simulation.graphics.entities.GraphicTile;
import com.NV.simulation.graphics.entities.GraphicalCloud;
import com.NV.simulation.tile.Tile;
import com.NV.simulation.weather.clouds.Cloud;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class Application extends javafx.application.Application {

    public static Group animalGroup = new Group();
    public static Group tileGroup = new Group();
    public static Group cloudGroup = new Group();


    private final static Deque<Runnable> callbackList = new LinkedList<>();

    public synchronized static void addCallbackFunction(Runnable runnable)
    {
        callbackList.addLast(runnable);
    }

    public static void shuffleEntities()
    {
        animalGroup.getChildren().forEach(animal->((GraphicAnimal)animal).shufflePosition());
    }

    public static void updateSimulationState() {
        animalGroup.getChildren().forEach(a->((GraphicAnimal)a).uninstallTooltip());
        animalGroup.getChildren().clear();
        tileGroup.getChildren().forEach(a->((GraphicTile)a).uninstallTooltip());
        tileGroup.getChildren().clear();
        cloudGroup.getChildren().clear();

        List<Tile> tilemap = MasterData.map.getList();
        for (Tile tile : tilemap) {
            if(tile != null)
            tileGroup.getChildren().add(new GraphicTile(tile));
        }

        List<Animal> animals = MasterData.animalManager.getList();
        for (Animal animal : animals) {
            if(animal != null)
            animalGroup.getChildren().add(new GraphicAnimal(animal));
        }

        List<Cloud> cloudList = MasterData.weatherManager.getList();
        for (Cloud cloud : cloudList) {
            if(cloud != null)
            cloudGroup.getChildren().add(new GraphicalCloud(cloud));
        }
    }


    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/com/NV/simulation/resources/UI/MainUI.fxml"));

        Parent root = loader.load();
        root.setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY)
                MasterData.animalPlacer.disable();
        });
        MasterData.mainUIController = loader.getController();
        MasterData.tileMap = MasterData.mainUIController.tilemap;
        MasterData.tileMap.getChildren().addAll(tileGroup,animalGroup,cloudGroup,MasterData.animalPlacer.placer);

        VBox actionBar = MasterData.mainUIController.actionBar;

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/resources/UI/TileEditor.fxml"));
        actionBar.getChildren().add(loader.load());
        MasterData.tileEditController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/resources/UI/WindIndicator.fxml"));
        actionBar.getChildren().add(loader.load());

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/resources/UI/SimpleActions.fxml"));
        actionBar.getChildren().add(loader.load());

        loader = new FXMLLoader(getClass().getResource("/com/NV/simulation/resources/UI/AddAnimal.fxml"));
        actionBar.getChildren().add(loader.load());
        MasterData.addAnimalController = loader.getController();


        MasterData.mainWindow = MasterData.mainUIController.tilemap.getScene().getWindow();

        primaryStage.setTitle("Simulation");
        primaryStage.setOnCloseRequest(e->{
        try{
            MasterData.statsController.stop();
        }
        catch(Exception err){}
        });
        updateSimulationState();
        shuffleEntities();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                MasterData.animalPlacer.update();
                if(callbackList.size()>0)
                {
                    callbackList.getFirst().run();
                    callbackList.removeFirst();
                }
            }
        }.start();

    }
}
