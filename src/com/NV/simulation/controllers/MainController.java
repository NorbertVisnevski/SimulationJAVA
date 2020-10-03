package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
import com.NV.simulation.animals.Animal;
import com.NV.simulation.animals.AnimalHerbivore;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

    public boolean updateRender = false;

    @FXML
    public Pane tilemap;

    @FXML
    public void nextTurn(ActionEvent event)
    {
            MasterData.animalManager.update();
            updateRender=true;
    }

    @FXML
    public void actionWindow(ActionEvent event) {
        Stage secondStage = new Stage();
        Button btn = new Button();
        btn.setText("Next turn");
        btn.setOnAction(e->{MasterData.animalManager.update();
                    updateRender=true;});
        secondStage.initStyle(StageStyle.UTILITY);
        secondStage.initOwner(tilemap.getScene().getWindow());
        secondStage.setScene(new Scene(new HBox(7, new Label("Second window"),btn)));
        secondStage.show();
    }
    @FXML
    public void statWindow(ActionEvent event) {
        Stage secondStage = new Stage();
        VBox box = new VBox();
        box.getChildren().add(new Text(((Long)Animal.getCount()).toString()));
        box.getChildren().add(new Text(((Long) AnimalHerbivore.getCount()).toString()));
        secondStage.initStyle(StageStyle.UTILITY);
        secondStage.initOwner(tilemap.getScene().getWindow());
        secondStage.setScene(new Scene(box));
        secondStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                box.getChildren().clear();
                box.getChildren().add(new Text("Animals: " + ((Long)Animal.getCount()).toString()));
                box.getChildren().add(new Text("Herbivores: " + ((Long) AnimalHerbivore.getCount()).toString()));
            }
        }.start();

    }
}
