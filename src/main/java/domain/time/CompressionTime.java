package domain.time;

import java.time.Duration;

public interface CompressionTime {
    public void setCompressionStart();
    public void setCompressionStop();
    public Duration getCompressionDuration();
}
