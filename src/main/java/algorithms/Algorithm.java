package algorithms;

import domain.DataInfo;
import exceptions.AlgorithmException;

import java.io.File;

public interface Algorithm {
    public Algorithm setFileName(File file);
    public Algorithm setDestinationFolder(String file);
    public DataInfo getDataInfo();

    public DataInfo exec () throws AlgorithmException;
    public void reset () ;

    public DataInfo compress() throws AlgorithmException;
    public DataInfo decompress() throws AlgorithmException;

    public abstract String getAlgorithmName();
    public abstract String getAlgorithmKey();
    public abstract boolean isForText();
    public abstract boolean isForBinary();
}
