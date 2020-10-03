package com.simulation.graphics;

import com.simulation.MasterData;
import com.simulation.map.Tile;
import com.simulation.animals.Animal;
import com.simulation.animals.AnimalFox;
import com.simulation.animals.AnimalRabbit;
import com.simulation.animals.AnimalWolf;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;


//For rendering window
//TODO fix this mess
public class Application extends javafx.application.Application {
    private static boolean fullscreen = false;

    private static boolean updateRender = true;

    private Pane tileMap;
    private static int tilesPerRow = 4;
    private static int rows = 3;

    int xStartOffset = 30; // offsets the entire field to the right
    int yStartOffset = 60; // offsets the entire fiels downwards

    private final static int WINDOW_WIDTH = 2300;
    private final static int WINDOW_HEIGHT = 1100;

    private final static double r = 32; // the inner radius from hexagon center to outer corner
    private final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;

    private void updateSimulationState() {
        tileMap.getChildren().clear();
        List<Tile> tilemap = MasterData.map.getTileMap();
        for (Tile tile : tilemap) {
            double xCoord = tile.getPosition().x * TILE_WIDTH + (tile.getPosition().y % 2) * n + xStartOffset;
            double yCoord = tile.getPosition().y * TILE_HEIGHT * 0.75 + yStartOffset;
            Polygon t = new GTile(xCoord, yCoord, tile.getTerrainType());
            tileMap.getChildren().add(t);
        }

        List<Animal> animalmap = MasterData.animalManager.getAnimalList();
        for (Animal animal : animalmap) {
            double xCoord = animal.getLocation().x * TILE_WIDTH + (animal.getLocation().y % 2) * n + xStartOffset;
            double yCoord = animal.getLocation().y * TILE_HEIGHT * 0.75 + yStartOffset;
            Rectangle t = null;

            if (animal instanceof AnimalRabbit)
                t = new GAnimal(xCoord, yCoord, "Rabbit",animal);
            else if (animal instanceof AnimalWolf)
                t = new GAnimal(xCoord, yCoord, "Wolf",animal);
            else if (animal instanceof AnimalFox)
                t = new GAnimal(xCoord, yCoord, "Fox",animal);

            tileMap.getChildren().add(t);
        }
        updateRender=true;
    }


    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        /*AnchorPane mainPane = new AnchorPane();
        mainPane.setStyle("-fx-background-color: #000000;");
        tileMap = new Pane();
        mainPane.getChildren().add(tileMap);

        HBox hbox = new HBox();

        Button flscrbtn = new Button("fullscreen");
        flscrbtn.setOnAction(e -> {
            primaryStage.setFullScreen(!fullscreen);
            fullscreen = !fullscreen;
        });
        hbox.getChildren().add(flscrbtn);

        Button nexttrnbtn = new Button("next turn");
        nexttrnbtn.setOnAction(e -> {
            if(updateRender)
            {
                updateRender = false;
                MasterData.animalManager.update();
                updateSimulationState();

            }

        });
        hbox.getChildren().add(nexttrnbtn);

        mainPane.getChildren().add(hbox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(mainPane);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.setMaxSize(100,100);

        Scene content = new Scene(scrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(content);
        primaryStage.setFullScreenExitHint("");



        updateSimulationState();

        primaryStage.setFullScreen(false);
        primaryStage.setTitle("We live in a simulation");*/
        primaryStage.setScene(new Scene(root,500,500));
        primaryStage.show();

//        new AnimationTimer() {
//            @Override
//            public void handle(long currentNanoTime) {
//                if(updateRender){
//                    tileMap.getChildren().clear();
//                    List<Tile> tilemap = MasterData.map.getTileMap();
//                    for (Tile tile : tilemap) {
//                        double xCoord = tile.getPosition().x * TILE_WIDTH + (tile.getPosition().y % 2) * n + xStartOffset;
//                        double yCoord = tile.getPosition().y * TILE_HEIGHT * 0.75 + yStartOffset;
//                        Polygon t = new GTile(xCoord, yCoord, tile.getTerrainType());
//                        tileMap.getChildren().add(t);
//                    }
//
//                    List<Animal> animalmap = MasterData.animalManager.getAnimalList();
//                    for (Animal animal : animalmap) {
//                        double xCoord = animal.getLocation().x * TILE_WIDTH + (animal.getLocation().y % 2) * n + xStartOffset;
//                        double yCoord = animal.getLocation().y * TILE_HEIGHT * 0.75 + yStartOffset;
//                        Rectangle t = null;
//
//                        if (animal instanceof AnimalRabbit)
//                            t = new GAnimal(xCoord, yCoord, "Rabbit");
//                        else if (animal instanceof AnimalWolf)
//                            t = new GAnimal(xCoord, yCoord, "Wolf");
//                        else if (animal instanceof AnimalFox)
//                            t = new GAnimal(xCoord, yCoord, "Fox");
//
//                        tileMap.getChildren().add(t);
//                        updateRender =false;
//                    }
//                }
//            }
//        }.start();

    }


    private class GTile extends Polygon {
        GTile(double x, double y, String type) {

            getPoints().addAll(
                    x, y,
                    x, y + r,
                    x + n, y + r * 1.5,
                    x + TILE_WIDTH, y + r,
                    x + TILE_WIDTH, y,
                    x + n, y - r * 0.5
            );


            switch (type) {
                case Tile.TerrainTypes.PLANES:
                    setFill(Color.GREEN);
                    break;
                case Tile.TerrainTypes.WATER:
                    setFill(Color.BLUE);
                    break;
                case Tile.TerrainTypes.MOUNTAINS:
                    setFill(Color.BROWN);
                    break;
                case Tile.TerrainTypes.DESERT:
                    setFill(Color.YELLOW);
                    break;
                case Tile.TerrainTypes.HILLS:
                    setFill(Color.YELLOWGREEN);
                    break;
                case Tile.TerrainTypes.FOREST:
                    setFill(Color.DARKGREEN);
                    break;
                case Tile.TerrainTypes.MARSH:
                    setFill(Color.MEDIUMVIOLETRED);
                    break;
                case Tile.TerrainTypes.FROZEN_WASTELAND:
                    setFill(Color.LIGHTGREY);
                    break;

            }
            setStrokeWidth(1);
            setStroke(Color.BLACK);
        }
    }

    private class GAnimal extends Rectangle {
        GAnimal(double x, double y, String type, Animal animal) {
            setX(x + MasterData.random.nextInt(48));
            setY(y + MasterData.random.nextInt(16));
            setHeight(8);
            setWidth(8);
            switch (type) {
                case "Rabbit":
                    setFill(Color.WHITE);
                    break;
                case "Wolf":
                    setFill(Color.GREY);
                    break;
                case "Fox":
                    setFill(Color.ORANGE);
                    break;
            }
            setOnMouseClicked(e->
            {Button btn = new Button();
            btn.setLayoutX(x);
            btn.setLayoutY(y);
            btn.setText(animal.toString());
                tileMap.getChildren().add(btn);
            });
        }
    }
}
