package com.NV.simulation.graphics.dialogs;

import com.NV.simulation.MasterData;
import com.NV.simulation.formaters.NumberTextFormatter;
import com.NV.simulation.graphics.Application;
import com.NV.simulation.map.Tile;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.awt.*;
import java.util.Optional;

public class NewMapDialog {

    public NewMapDialog()
    {
        Dialog<Pair<Integer, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Generate new map");

        ButtonType loginButtonType = new ButtonType("Generate", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField width = new TextField("30");
        width.setTextFormatter(new NumberTextFormatter().getFormatter());
        TextField height = new TextField("21");
        height.setTextFormatter(new NumberTextFormatter().getFormatter());

        grid.add(new Label("Width:"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(height, 1, 1);

        dialog.getDialogPane().setContent(grid);

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>((int)Double.parseDouble(width.getText()), (int)Double.parseDouble(height.getText()));
            }
            return null;
        });

        Optional<Pair<Integer, Integer>> result = dialog.showAndWait();

        result.ifPresent(data -> {
            Application.addCallbackFunction(()->{
                MasterData.animalManager.clear();
                MasterData.weatherManager.clear();
                MasterData.map.clear();
                for(int i = 0; i < data.getKey(); ++i)
                {
                    for(int j = 0; j < data.getValue(); ++j)
                    {
                        MasterData.map.add(new Tile(new Point(i,j), Tile.TerrainTypes.PLANES, false, 1.0, 10.0));
                    }
                }
                MasterData.weatherManager.linkToMap(MasterData.map);
                Application.updateSimulationState();
            });
        });
    }
}
