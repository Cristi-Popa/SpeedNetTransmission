package controllers.viewdata;

import algorithms.Algorithm;
import javafx.beans.property.SimpleStringProperty;

import java.text.NumberFormat;

public class AlgProbe {

    SimpleStringProperty algorithm = new SimpleStringProperty();
    SimpleStringProperty rata = new SimpleStringProperty();


    SimpleStringProperty originalSize = new SimpleStringProperty();
    SimpleStringProperty dimensiune = new SimpleStringProperty();
    SimpleStringProperty worth = new SimpleStringProperty();

    public AlgProbe() {
    }

    public AlgProbe(Algorithm algorithm, NumberFormat nf, Integer originalSize) {
        this.algorithm.set(algorithm.getAlgorithmName());
        this.rata.set(algorithm.getDataInfo().getCompressionRate());
        this.originalSize.set(originalSize.toString());
        this.dimensiune.set(nf.format(algorithm.getDataInfo().getCompressedSize())+" bytes");
        this.worth.set(!algorithm.getDataInfo().getCompressionRate().contains("-")? "Worth to compress":"Not worth to compress");
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

    public String getWorth() {
        return worth.get();
    }

    public SimpleStringProperty worthProperty() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth.set(worth);
    }

    public String getOriginalSize() {
        return originalSize.get();
    }

    public SimpleStringProperty originalSizeProperty() {
        return originalSize;
    }

    public void setOriginalSize(String originalSize) {
        this.originalSize.set(originalSize);
    }
}
