package domain.speed;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TransferRate implements TransferSpeed {
    /**
     * Average duration measured in bytes
     */
    private Long sentAvg = -1L;
    private Long receiveAvg = -1L;

    private Long fileSize;

    private Long sentStartAvg;
    private Long sentStopAvg;

    private Long receiveStartAvg;
    private Long receiveStopAvg;

    @Override
    public void sent(Runnable sent) {
        sentStartAvg = System.nanoTime();
        sent.run();
        sentStopAvg = System.nanoTime();
        Duration duration = Duration.ofNanos(sentStopAvg - sentStartAvg);

        if (duration.getSeconds() == 0)
            sentAvg = fileSize;
        else
            sentAvg = fileSize / duration.getSeconds();
    }

    @Override
    public void receive(Runnable receive) {
        receiveStartAvg = System.nanoTime();
        receive.run();
        receiveStopAvg = System.nanoTime();

        Duration duration = Duration.ofNanos(receiveStopAvg - receiveStartAvg);
        if (duration.getSeconds() == 0)
            receiveAvg = fileSize;
        else
            receiveAvg = fileSize / duration.getSeconds();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getSentAvg() {
        return sentAvg;
    }

    public Long getReceiveAvg() {
        return receiveAvg;
    }

    public void reset() {
        sentAvg = -1L;
        receiveAvg = -1L;
    }
}
