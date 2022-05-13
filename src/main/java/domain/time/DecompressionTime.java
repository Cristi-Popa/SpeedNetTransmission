package domain.time;

import java.time.Duration;

public interface DecompressionTime {

    public void setDecompressionStart();
    public void setDecompressionStop();

    public Duration getDecompressionDuration();

}
