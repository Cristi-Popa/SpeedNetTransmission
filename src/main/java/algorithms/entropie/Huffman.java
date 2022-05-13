package algorithms.entropie;

import algorithms.AbstractAlgoritm;
import domain.DataInfo;
import exceptions.AlgorithmException;

public class Huffman extends AbstractAlgoritm {
    @Override
    public DataInfo compress() throws AlgorithmException {
        return null;
    }

    @Override
    public DataInfo decompress() throws AlgorithmException {
        return null;
    }

    @Override
    public String getAlgorithmName() {
        return "Huffman Coding";
    }

    @Override
    public String getAlgorithmKey() {
        return "Huffman";
    }

    @Override
    public boolean isForText() {
        return true;
    }

    @Override
    public boolean isForBinary() {
        return false;
    }
}
