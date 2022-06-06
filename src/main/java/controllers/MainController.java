package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends MyDataController {
    @Override
    public void sentChosenFile(ActionEvent actionEvent) {
        try {
            client.send(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
