package com.NV.simulation.controllers;

import com.NV.simulation.animals.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StatsController {
    @FXML
    private TableView statTable;

    @FXML
    private PieChart herbCarChart;

    private ObservableList<TableEntry> tableData = FXCollections.observableArrayList();

    private ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(new PieChart.Data("Herbivores", AnimalHerbivore.getCount()),new PieChart.Data("Carnivores", AnimalCarnivore.getCount()));

    private Thread thread;

    public void stop()
    {
        thread.interrupt();
    }

    @FXML
    public void initialize() {

        TableColumn<TableEntry, String> column1 = new TableColumn<>("Animal type");
        TableColumn<TableEntry, String> column2 = new TableColumn<>("Count");
        column1.setCellValueFactory(cellData ->cellData.getValue().name);
        column2.setCellValueFactory(cellData ->cellData.getValue().count.asString());
        column1.setEditable(false);
        column2.setEditable(false);
        statTable.getColumns().add(column1);
        statTable.getColumns().add(column2);


        tableData.add(new TableEntry("Animal",AnimalBase.getCount()));
        tableData.add(new TableEntry("Herbivore", AnimalHerbivore.getCount()));
        tableData.add(new TableEntry("Carnivore", AnimalCarnivore.getCount()));
        tableData.add(new TableEntry("Rabbit", AnimalRabbit.getCount()));
        tableData.add(new TableEntry("Fox", AnimalFox.getCount()));
        tableData.add(new TableEntry("Wolf", AnimalWolf.getCount()));
        for(TableEntry entry : tableData)
        statTable.getItems().add(entry);


        herbCarChart.setData(piechartData);

        thread = new Thread(()-> {
            try{
                while(!Thread.interrupted())
                {
                    //System.out.println("hello");
                    tableData.get(0).count.set(AnimalBase.getCount());
                    tableData.get(1).count.set(AnimalHerbivore.getCount());
                    tableData.get(2).count.set(AnimalCarnivore.getCount());
                    tableData.get(3).count.set(AnimalRabbit.getCount());
                    tableData.get(4).count.set(AnimalFox.getCount());
                    tableData.get(5).count.set(AnimalWolf.getCount());

                    piechartData.get(0).setPieValue(AnimalHerbivore.getCount());
                    piechartData.get(1).setPieValue(AnimalCarnivore.getCount());
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        });
        thread.start();
    }

    private class TableEntry{
        public LongProperty count ;
        public StringProperty name ;
        public TableEntry(String name, long count) {
            this.count = new SimpleLongProperty(count) ;
            this.name = new SimpleStringProperty(name) ;
        }

    }
}
