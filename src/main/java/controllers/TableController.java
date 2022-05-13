package controllers;

import algorithms.Algorithm;
import controllers.viewdata.AlgPerformance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class TableController extends AlgorithmsController{
    public TableView TABLE_VIEW;
    private ObservableList<AlgPerformance> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        TABLE_VIEW.setItems(data);
    }

    @Override
    protected void updateTable(Algorithm algorithm) {
        data.add(new AlgPerformance(algorithm, nf));
    }

    @Override
    protected void clearTable() {
        data.clear();
    }
}
