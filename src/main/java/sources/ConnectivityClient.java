package sources;

import config.Prop;
import domain.speed.TransferRate;

import java.io.*;
import java.net.Socket;

public class ConnectivityClient {
    TransferRate transferRate = new TransferRate();
    public ConectivityStatus status = ConectivityStatus.READY;

    private Long totalSentBytesCount = 0L; // every one second it is reset to 0, to get instant speed

    private String ip;
    private Integer port;

    private String password;

    public void setConnectData(String ipAndPort, String password) throws IOException {
        String[] ipPort = ipAndPort.split(":");
        Socket socket = new Socket(ipPort[0], Integer.parseInt(ipPort[1]));

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        outputStream.writeUTF(password);
        outputStream.writeUTF(ConectivityStatus.CONNECTED.name());

        if (ConectivityStatus.CONNECTED.name().equals(inputStream.readUTF())) {
            status = ConectivityStatus.CONNECTED;
            this.ip = ipPort[0];
            this.port = Integer.parseInt(ipPort[1]);
            this.password = password;
        } else {
            status = ConectivityStatus.NOT_CONNECTED;
        }
    }

    public void send(File file) throws IOException {
        new Thread(() -> {
            try {
                transferRate.setFileSize(file.length());
                Socket socket = new Socket(ip, port);

                status = ConectivityStatus.SENDING;
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(password);
                outputStream.writeUTF(ConectivityStatus.SENDING.name());

                transferRate.sent(() -> {
                    try {
                        sent(socket, file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                status = ConectivityStatus.DONE_SENT;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void sent(Socket socket, File file) throws IOException {
        FileInputStream inputStream = null;
        DataOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[Prop.BUFFER_FILE_TRANSFER];
            outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF(file.getName());

            int receivedBytesCount = -1;
            while ((receivedBytesCount = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, receivedBytesCount);
                totalSentBytesCount += receivedBytesCount;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
            socket.close();
        }
    }

    public Long getAverageSentTransferRate() {
        return transferRate.getSentAvg();
    }

    public Long getSentBytesPerSecond() {
        long aux = totalSentBytesCount;
        totalSentBytesCount = 0L;
        return aux;
    }

    public void resetTransferRate() {
        transferRate.reset();
    }
}
