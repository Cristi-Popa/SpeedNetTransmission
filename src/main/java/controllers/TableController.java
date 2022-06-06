package controllers;

import algorithms.Algorithm;
import controllers.viewdata.AlgPerformance;
import controllers.viewdata.AlgProbe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class TableController extends AlgorithmsController{
    public TableView TABLE_VIEW;
    private ObservableList<AlgPerformance> data = FXCollections.observableArrayList();
    private ObservableList<AlgProbe> dataProbe = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        initColumns();
    }

    @Override
    protected void updateTable(Algorithm algorithm) {
        setTableForEncoding();
        TABLE_VIEW.setItems(data);
        data.add(new AlgPerformance(algorithm, nf));
    }

    @Override
    protected void updateProbeTable(Algorithm algorithm, int originalFileSize) {
        setTableForEntropy();
        TABLE_VIEW.setItems(dataProbe);
        dataProbe.add(new AlgProbe(algorithm, nf, originalFileSize));
    }
    @Override
    protected void clearTable() {
        data.clear();
        dataProbe.clear();
    }

    protected void setTableForEncoding(){
        TABLE_VIEW.getColumns().clear();
        TABLE_VIEW.getColumns().addAll(
                Algoritm,
                Rata,
                DimC,
                TimpC,
                TimpD
        );
    }

    protected void setTableForEntropy(){
        TABLE_VIEW.getColumns().clear();
        TABLE_VIEW.getColumns().addAll(
                Algoritm,
                Rata,
                DimC,
                originalSize,
                Worth
        );
    }

    protected void initColumns(){
        Algoritm.setCellValueFactory(new PropertyValueFactory<>("algorithm"));
        Rata.setCellValueFactory(new PropertyValueFactory<>("rata"));
        DimC.setCellValueFactory(new PropertyValueFactory<>("dimensiune"));
        TimpC.setCellValueFactory(new PropertyValueFactory<>("timpCompression"));
        TimpD.setCellValueFactory(new PropertyValueFactory<>("timpDecompression"));

        Worth.setCellValueFactory(new PropertyValueFactory<>("worth"));
        originalSize.setCellValueFactory(new PropertyValueFactory<>("originalSize"));
    }

    public TableColumn Algoritm = new TableColumn("Algoritm");
    public TableColumn Rata = new TableColumn("Rata");
    public TableColumn DimC = new TableColumn("Dimensiune C");
    public TableColumn TimpC = new TableColumn("Timp C");
    public TableColumn TimpD = new TableColumn("Timp D");

    public TableColumn Worth = new TableColumn("Worth");
    public TableColumn originalSize = new TableColumn("Dimensiunea originala");

}
