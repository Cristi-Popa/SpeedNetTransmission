package sources;

import algorithms.tehnici.BurrowsWheelerTransform;
import controllers.ConnectionController;
import domain.speed.TransferSpeed;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static List<Boolean> THREAD_STOPS= new ArrayList<>(){{add(false);}};

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Speed Net Transmission");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
        ConnectionController.timer.cancel();
        ConnectionController.timer.purge();
        synchronized (THREAD_STOPS) {
            for (int i = 0; i < THREAD_STOPS.size(); i++) {
                THREAD_STOPS.set(i, true);
            }
        }
        System.exit(0);
    }
}
