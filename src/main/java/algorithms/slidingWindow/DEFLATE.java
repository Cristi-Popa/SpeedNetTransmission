package algorithms.slidingWindow;

import algorithms.ApacheAlgorithms;
import domain.DataInfo;
import exceptions.AlgorithmException;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;

public class DEFLATE extends ApacheAlgorithms {

    @Override
    public DataInfo compress() throws AlgorithmException {
        dataInfo.setCompressionStart();
        genericCompress(DeflateCompressorOutputStream::new);
        dataInfo.setCompressionStop();
        return dataInfo;
    }

    @Override
    public DataInfo decompress() throws AlgorithmException {
        dataInfo.setDecompressionStart();
        genericDecompress(DeflateCompressorInputStream::new);
        dataInfo.setDecompressionStop();
        return dataInfo;
    }

    @Override
    public String getAlgorithmName() {
        return "DEFLATE";
    }

    @Override
    public String getAlgorithmKey() {
        return "DEFLATE";
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
