package com.NV.simulation.controllers;

import com.NV.simulation.MasterData;
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

import java.util.List;

public class StatsController {
    @FXML
    private TableView statTable;

    @FXML
    private PieChart herbCarChart;

    private final ObservableList<TableEntry> tableData = FXCollections.observableArrayList();

    private ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(new PieChart.Data("Herbivores", 0),new PieChart.Data("Carnivores", 0));

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


        tableData.add(new TableEntry("Animal",0));
        tableData.add(new TableEntry("Herbivore", 0));
        tableData.add(new TableEntry("Carnivore", 0));
        tableData.add(new TableEntry("Rabbit", 0));
        tableData.add(new TableEntry("Fox", 0));
        tableData.add(new TableEntry("Wolf", 0));
        for(TableEntry entry : tableData)
        statTable.getItems().add(entry);


        herbCarChart.setData(piechartData);

        thread = new Thread(()-> {
            try{
                int carnivores=0
                    ,herbivores=0
                    ,rabbits=0
                    ,wolfs=0
                    ,foxes=0;

                while(!Thread.interrupted())
                {
                    List<Animal> list = MasterData.animalManager.getList();
                    tableData.get(0).count.set(list.size());
                    for(Animal animal : list)
                    {
                        if(animal instanceof AnimalWolf)
                        {
                            ++wolfs;
                            ++carnivores;
                        }
                        else if(animal instanceof AnimalFox)
                        {
                            ++foxes;
                            ++carnivores;
                        }
                        else if(animal instanceof AnimalRabbit)
                        {
                            ++rabbits;
                            ++herbivores;
                        }
                    }
                    tableData.get(1).count.set(herbivores);
                    tableData.get(2).count.set(carnivores);
                    tableData.get(3).count.set(rabbits);
                    tableData.get(4).count.set(foxes);
                    tableData.get(5).count.set(wolfs);

                    piechartData.get(0).setPieValue(herbivores);
                    piechartData.get(1).setPieValue(carnivores);

                    carnivores=0;
                    herbivores=0;
                    rabbits=0;
                    wolfs=0;
                    foxes=0;

                    Thread.sleep(1000);
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
