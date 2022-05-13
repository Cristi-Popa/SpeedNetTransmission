package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import sources.ConectivityStatus;
import sources.ConnectivityClient;
import sources.ConnectivityServer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public abstract class ConnectionController extends TableController {

    public static Timer timer = new Timer(true);
    public Label SPEED_LABEL;
    public Label STATUS_LABEL;
    public Label AVG_SPEED_LABEL;

    public ConnectivityClient client;
    private ConectivityStatus clientStatus;
    protected ConnectivityServer server;
    private ConectivityStatus serverStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        server = new ConnectivityServer();
        server.startListeningServer();
        client = new ConnectivityClient();

        clientStatus = client.status;
        serverStatus = server.status;
        STATUS_LABEL.setText(serverStatus.name());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!serverStatus.equals(server.status)) {
                        serverStatus = server.status;
                        STATUS_LABEL.setText(serverStatus.name());
                    }
                    if (server.status.equals(ConectivityStatus.RECEIVING)) {
                        SPEED_LABEL.setText(nf.format(server.getReceiveBytesPerSecond()) + " bytes");
                    }
                    if ((server.status.equals(ConectivityStatus.DONE_RECV) || server.status.equals(ConectivityStatus.READY)) &&
                            server.getAverageReceivedTransferRate() != -1L) {
                        SPEED_LABEL.setText(0 + " bytes");
                        AVG_SPEED_LABEL.setText(nf.format(server.getAverageReceivedTransferRate()) + " bytes");
                        server.resetTransferRate();
                    }

                    if (!clientStatus.equals(client.status)) {
                        clientStatus = client.status;
                        STATUS_LABEL.setText(clientStatus.name());
                    }
                    if (client.status.equals(ConectivityStatus.SENDING)) {
                        SPEED_LABEL.setText(nf.format(client.getSentBytesPerSecond()) + " bytes");
                    }
                    if ((client.status.equals(ConectivityStatus.DONE_SENT) || client.status.equals(ConectivityStatus.READY)) &&
                            client.getAverageSentTransferRate() != -1L) {
                        SPEED_LABEL.setText(0 + " bytes");
                        AVG_SPEED_LABEL.setText(nf.format(client.getAverageSentTransferRate()) + " bytes");
                        client.resetTransferRate();
                    }
                });
            }
        }, 1000, 1000);

    }

    public void connectPopUp(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Destination");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField from = new TextField();
        from.setPromptText("IpP & Port");
        TextField to = new TextField();
        to.setPromptText("Password");

        gridPane.add(new Label("IpP & Port:"), 0, 0);
        gridPane.add(from, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(from::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        new Thread(() -> {
            result.ifPresent(pair -> {
                try {
                    client.setConnectData(pair.getKey(), pair.getValue());
                    enableSent();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    @Override
    protected void enableSent() {
        if (client.status.equals(ConectivityStatus.CONNECTED) && file!=null) {
            SENT_CHOSEN_FILE_BUTTON.setDisable(false);
        }
    }
}
