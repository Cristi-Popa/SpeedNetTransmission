package controllers.viewdata;

import algorithms.Algorithm;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.NumberFormat;
import java.time.temporal.TemporalUnit;

public class AlgPerformance {
    SimpleStringProperty algorithm = new SimpleStringProperty();
    SimpleStringProperty rata = new SimpleStringProperty();
    SimpleStringProperty dimensiune = new SimpleStringProperty();
    SimpleStringProperty timpCompression = new SimpleStringProperty();
    SimpleStringProperty timpDecompression =new SimpleStringProperty();

    public AlgPerformance() {
    }

    public AlgPerformance(Algorithm algorithm, NumberFormat nf) {
        this.algorithm.set(algorithm.getAlgorithmName());
        this.rata.set(algorithm.getDataInfo().getCompressionRate());
        this.dimensiune.set(nf.format(algorithm.getDataInfo().getCompressedSize())+" bytes");
        this.timpCompression.set(nf.format((double) algorithm.getDataInfo().getCompressionDuration().toMillis()/1000)+" s");
        this.timpDecompression.set(nf.format((double) algorithm.getDataInfo().getDecompressionDuration().toMillis()/1000)+" s");
    }

    public String getAlgorithm() {
        return algorithm.get();
    }

    public SimpleStringProperty algorithmProperty() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm.set(algorithm);
    }

    public String getRata() {
        return rata.get();
    }

    public SimpleStringProperty rataProperty() {
        return rata;
    }

    public void setRata(String rata) {
        this.rata.set(rata);
    }

    public String getDimensiune() {
        return dimensiune.get();
    }

    public SimpleStringProperty dimensiuneProperty() {
        return dimensiune;
    }

    public void setDimensiune(String dimensiune) {
        this.dimensiune.set(dimensiune);
    }

    public String getTimpCompression() {
        return timpCompression.get();
    }

    public SimpleStringProperty timpCompressionProperty() {
        return timpCompression;
    }

    public void setTimpCompression(String timpCompression) {
        this.timpCompression.set(timpCompression);
    }

    public String getTimpDecompression() {
        return timpDecompression.get();
    }

    public SimpleStringProperty timpDecompressionProperty() {
        return timpDecompression;
    }

    public void setTimpDecompression(String timpDecompression) {
        this.timpDecompression.set(timpDecompression);
    }
}
