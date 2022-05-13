package sources;

import config.Prop;
import domain.speed.TransferRate;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectivityServer {
    public ServerSocket serverSocket = null;
    private TransferRate transferRate = new TransferRate();

    private Long totalReceivedBytesCount = 0L; // every one second it is reset to 0, to get instant speed
    public ConectivityStatus status = ConectivityStatus.NA;
    private String downloadFolder = System.getProperty("user.home") + "/Downloads/";

    private String password="-";

    private String fileName ="";

    public void startListeningServer() {
        new Thread(() -> {
            while (!Main.THREAD_STOPS.get(0)) {
                startServer();
                status = ConectivityStatus.READY;
                Socket socket = waitForAClient();
                status = ConectivityStatus.RECEIVING;
                transferRate.receive(()->{
                    receive(socket);
                    transferRate.setFileSize(new File(downloadFolder + fileName).length());
                });

                status = ConectivityStatus.DONE_RECV;
            }
        }).start();
    }

    private void receive(Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            fileName = dataInputStream.readUTF();
            FileOutputStream fout = new FileOutputStream(downloadFolder + fileName);

            byte[] buffer = new byte[Prop.BUFFER_FILE_TRANSFER];
            int receivedBytesCount = -1;

            while ((receivedBytesCount = dataInputStream.read(buffer)) != -1) {
                totalReceivedBytesCount += receivedBytesCount;
                fout.write(buffer, 0, receivedBytesCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Socket waitForAClient() {
        try {
            String pass="";
            Socket socket = null;
            String stat=ConectivityStatus.NA.name();
            while (!password.equals(pass) || stat.equals(ConectivityStatus.CONNECTED.name())){
                socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                pass = dataInputStream.readUTF();
                stat = dataInputStream.readUTF();
                if (stat.equals(ConectivityStatus.CONNECTED.name())){
                    outputStream.writeUTF(ConectivityStatus.CONNECTED.name());
                }
            }
            return socket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startServer() {
        while (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(Prop.PORT);
            } catch (IOException ignored) {
                Prop.PORT++;
            }
        }
    }

    public Long getAverageReceivedTransferRate() {
        return transferRate.getReceiveAvg();
    }

    public void resetTransferRate() {
        transferRate.reset();
    }

    public Long getReceiveBytesPerSecond() {
        long aux = totalReceivedBytesCount;
        totalReceivedBytesCount = 0L;
        return aux;
    }

    public void setPass(String pass) {
        password=pass;
    }
}
