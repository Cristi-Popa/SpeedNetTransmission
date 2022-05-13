package controllers;

import config.Prop;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.RandomStringUtils;
import sources.ConnectivityServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ResourceBundle;

public abstract class MyDataController extends ConnectionController {
    public TextField MY_IP_PORT_FIELD;
    public TextField MY_PASSWORD_FIELD;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        MY_IP_PORT_FIELD.setEditable(false);
        MY_PASSWORD_FIELD.setEditable(false);
        generateMyPassword(null);
        Platform.runLater(()->{
            try {
//                URL ipAdress = new URL("http://myexternalip.com/raw");
//                BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
//                String ip = in.readLine();
                String ip = InetAddress.getLocalHost().getHostAddress();
                MY_IP_PORT_FIELD.setText(ip+":"+Prop.PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    public void generateMyPassword(ActionEvent actionEvent) {
        MY_PASSWORD_FIELD.setText(RandomStringUtils.randomPrint(10));
        server.setPass(MY_PASSWORD_FIELD.getText());
    }
}
