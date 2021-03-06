package controllers;

import algorithms.Algorithm;
import algorithms.nondictionary.BZIP2;
import algorithms.slidingWindow.DEFLATE;
import algorithms.slidingWindow.LZMA;
import algorithms.tehnici.BurrowsWheelerTransform;
import algorithms.tehnici.RunLenghEncoding;
import domain.DataInfo;
import exceptions.AlgorithmException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.*;

public abstract class AlgorithmsController extends FileController {
    public CheckBox RLE_CHECKBOX;
    public CheckBox BWT_CHECKBOX;
    public CheckBox LZMA_CHECKBOX;
    public CheckBox DEFLATE_CHECKBOX;
    public CheckBox BZIP2_CHECKBOX;

    public Button ALGORITHM_TEST_BUTTON;

    public TextField NO_BLOCKS_FIELD;
    public TextField BLOCK_SIZE_FIELD;
    public Button PROBE_BUTTON;


    public final Map<String, Algorithm> list = new HashMap<>();

    private static final String temporalDirectory = System.getProperty("java.io.tmpdir");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        setAlgorithmsDisable(true);
        setFieldConditions();
    }

    private void setFieldConditions() {
        NO_BLOCKS_FIELD.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!isIntegerOrNegative(NO_BLOCKS_FIELD.getText()))
                        showErrIntegerAlert();
                });
        BLOCK_SIZE_FIELD.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!isIntegerOrNegative(BLOCK_SIZE_FIELD.getText()))
                        showErrIntegerAlert();
                });
    }

    private void showErrIntegerAlert() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText("The contents of the field is not a number!");
        errorAlert.showAndWait();
    }

    private boolean isIntegerOrNegative(String textField) {
        try {
            int val =Integer.parseInt(textField);
            if (val>0)
                return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    protected void setAlgorithmsDisable(boolean disable) {
        RLE_CHECKBOX.setDisable(disable);
        BWT_CHECKBOX.setDisable(disable);
        LZMA_CHECKBOX.setDisable(disable);
        DEFLATE_CHECKBOX.setDisable(disable);
        BZIP2_CHECKBOX.setDisable(disable);
        ALGORITHM_TEST_BUTTON.setDisable(disable);
    }

    public void testCompressDecompressTime(ActionEvent actionEvent) {
        clearTable();
        list.forEach((s, algorithm) -> {
            Thread t = new Thread(() -> {
                try {
                    algorithm.exec();
                    Platform.runLater(() -> {
                        updateTable(algorithm);
                    });
                } catch (AlgorithmException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Execution error for algorithm " + algorithm.getAlgorithmName());
                    a.setContentText(e.getMessage());
                    a.show();
                }
            });
            t.start();
            new Thread(() -> {
                try {
                    t.join(1000 * 60 * 10);
                    if (t.isAlive()) {
                        t.interrupt();

                    }
                } catch (InterruptedException e) {
                    algorithm.reset();
                }
            }).start();
        });
    }

    public void probeAlgs(ActionEvent actionEvent) {
        clearTable();
        DataInfo di = new DataInfo();
        di.setFile(file);
//        di.setDestinationFolder(temporalDirectory);
        int noBlock = Integer.parseInt(NO_BLOCKS_FIELD.getText());
        int blockSize = Integer.parseInt(BLOCK_SIZE_FIELD.getText());
        File probeFile = di.createProbeFile(noBlock, blockSize);

        list.forEach((s, algorithm) -> {
            Thread t = new Thread(() -> {
                try {
                    algorithm.probe(probeFile);
                    Platform.runLater(() -> {
                        algorithm.setFileName(probeFile);
                        updateProbeTable(algorithm, noBlock * blockSize);
                        algorithm.setFileName(file);
                    });
                } catch (AlgorithmException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Execution error for algorithm " + algorithm.getAlgorithmName());
                    a.setContentText(e.getMessage());
                    a.show();
                }
            });
            t.start();
            new Thread(() -> {
                try {
                    t.join(1000 * 60 * 10);
                    if (t.isAlive()) {
                        t.interrupt();

                    }
                } catch (InterruptedException e) {
                    algorithm.reset();
                }
            }).start();
        });
    }

    protected abstract void updateTable(Algorithm algorithm);

    protected abstract void updateProbeTable(Algorithm algorithm, int originalFileSize);

    protected abstract void clearTable();

    public void checkRLE(ActionEvent actionEvent) {
        check(RLE_CHECKBOX, new RunLenghEncoding());
    }

    public void checkBWT(ActionEvent actionEvent) {
        check(BWT_CHECKBOX, new BurrowsWheelerTransform());
    }

    public void checkLZMA(ActionEvent actionEvent) {
        check(LZMA_CHECKBOX, new LZMA());
    }

    public void checkDEFLATE(ActionEvent actionEvent) {
        check(DEFLATE_CHECKBOX, new DEFLATE());
    }

    public void checkBZIP2(ActionEvent actionEvent) {
        check(BZIP2_CHECKBOX, new BZIP2());
    }


    private void check(CheckBox checkBox, Algorithm algorithm) {
        if (checkBox.isSelected())
            list.put(algorithm.getAlgorithmKey(), algorithm
                    .setFileName(file));
            //.setDestinationFolder(temporalDirectory));
        else
            list.remove(algorithm.getAlgorithmKey());
    }
}
