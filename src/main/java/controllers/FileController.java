package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class FileController implements Initializable {

    public Label FILE_NAME_LABEL;
    public Label FILE_SIZE_LABEL;
    public Button SENT_CHOSEN_FILE_BUTTON;

    protected File file=null;
    protected final NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SENT_CHOSEN_FILE_BUTTON.setDisable(true);
        SENT_CHOSEN_FILE_BUTTON.setTooltip(new Tooltip("Needs to be connected and test Compress and Decompress time"));
    }

    private void reset(){
        FILE_NAME_LABEL.setText("N/A");
        FILE_SIZE_LABEL.setText("N/A");
    }

    private void setLabels(File file){
        this.file = file;
        FILE_NAME_LABEL.setText(file.getName());


        FILE_SIZE_LABEL.setText(nf.format(file.length())+" bytes");
    }

    public void choseLocalFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("All Files", "*.*"),
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("CSV Files", "*.csv"),
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));

        Window windows = FILE_NAME_LABEL.getScene().getWindow();
        File file =  fileChooser.showOpenDialog(windows);
        if (file == null) {
            reset();
            Alert a =new Alert(AlertType.ERROR);
            a.setTitle("File not found");
            a.setContentText("File could not be found or loaded!");
            a.show();
            setAlgorithmsDisable(true);
        }else{
            setLabels(file);
            enableSent();
            setAlgorithmsDisable(false);
        }
    }

    protected abstract void enableSent();
    protected abstract void setAlgorithmsDisable(boolean disable);
    public abstract void sentChosenFile(ActionEvent actionEvent);
}
