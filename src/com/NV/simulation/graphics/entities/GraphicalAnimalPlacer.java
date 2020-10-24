package com.NV.simulation.graphics.entities;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.TriangleMesh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalAnimalPlacer extends Polygon {

    public GraphicalAnimalPlacer()
    {
        setVisible(false);
        getPoints().addAll(
                -10.0,-20.0,
                0.0,0.0,
                10.0,-20.0
        );
        setFill(Color.WHITE);
        setOpacity(0.5);
    }

    public void setLocation(Point p)
    {
        setLayoutX(p.x);
        setLayoutY(p.y);
    }

}
