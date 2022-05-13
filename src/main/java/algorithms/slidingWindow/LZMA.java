package algorithms.slidingWindow;

import algorithms.ApacheAlgorithms;
import domain.DataInfo;
import exceptions.AlgorithmException;
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;

public class LZMA extends ApacheAlgorithms {
    @Override
    public DataInfo compress() throws AlgorithmException {
        dataInfo.setCompressionStart();
        genericCompress(LZMACompressorOutputStream::new);
        dataInfo.setCompressionStop();
        return dataInfo;
    }

    @Override
    public DataInfo decompress() throws AlgorithmException {
        dataInfo.setDecompressionStart();
        genericDecompress(LZMACompressorInputStream::new);
        dataInfo.setDecompressionStop();
        return dataInfo;
    }

    @Override
    public String getAlgorithmName() {
        return "Lempel-Ziv Markov chain Algorithm";
    }

    @Override
    public String getAlgorithmKey() {
        return "LZMA";
    }

    @Override
    public boolean isForText() {
        return true;
    }

    @Override
    public boolean isForBinary() {
        return true;
    }
}
