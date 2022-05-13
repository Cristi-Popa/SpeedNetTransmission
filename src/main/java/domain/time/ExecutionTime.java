package domain.time;

import java.time.Duration;

public class ExecutionTime implements CompressionTime, DecompressionTime {
    private long compressionStart;
    private long compressionStop;

    private long decompressionStart;
    private long decompressionStop;

    @Override
    public void setDecompressionStart() {
        this.decompressionStart = System.nanoTime();
    }

    @Override
    public void setDecompressionStop() {
        this.decompressionStop = System.nanoTime();
    }

    @Override
    public Duration getDecompressionDuration() {
        return Duration.ofNanos(decompressionStop - decompressionStart);
    }

    @Override
    public void setCompressionStart() {
        this.compressionStart = System.nanoTime();
    }

    @Override
    public void setCompressionStop() {
        this.compressionStop = System.nanoTime();
    }

    @Override
    public Duration getCompressionDuration() {
        return Duration.ofNanos(compressionStop - compressionStart);
    }
}
