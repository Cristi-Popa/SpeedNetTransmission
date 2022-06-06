package algorithms.nondictionary;

import algorithms.ApacheAlgorithms;
import domain.DataInfo;
import exceptions.AlgorithmException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

public class BZIP2 extends ApacheAlgorithms {


    @Override
    public DataInfo compress() throws AlgorithmException {
        dataInfo.setCompressionStart();
        genericCompress(BZip2CompressorOutputStream::new);
        dataInfo.setCompressionStop();
        return dataInfo;
    }

    @Override
    public DataInfo decompress() throws AlgorithmException {
        dataInfo.setDecompressionStart();
        genericDecompress(BZip2CompressorInputStream::new);
        dataInfo.setDecompressionStop();
        return dataInfo;
    }

    @Override
    public String getAlgorithmName() {
        return "BZIP2";
    }

    @Override
    public String getAlgorithmKey() {
        return "BZIP2";
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
