package com.NV.simulation.graphics.dialogs;

import javafx.scene.control.Alert;

public class ErrorDialog {
    public ErrorDialog()
    {
        this("Error Dialog");
    }

    public ErrorDialog(String title)
    {
        this(title,"Ooops, there was an error!");
    }

    public ErrorDialog(String title, String context)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.show();
    }

    public ErrorDialog(String title,String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.show();
    }
}
