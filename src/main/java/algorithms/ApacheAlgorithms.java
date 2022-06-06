package algorithms;

import config.Constants;
import exceptions.AlgorithmException;
import exceptions.FunctionException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ApacheAlgorithms extends AbstractAlgoritm {

    public void genericCompress(FunctionException<BufferedOutputStream,CompressorOutputStream, IOException> compressor) throws AlgorithmException {
        createFolderIfNotExists(Paths.get(dataInfo.getCompressPath() + "\\" + dataInfo.getCompressFileName()));

        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(
                Paths.get(dataInfo.getCompressPath() + "\\" + dataInfo.getCompressFileName())));
             InputStream in = Files.newInputStream(Paths.get(dataInfo.getPath() + "\\" + dataInfo.getFileName()));
             CompressorOutputStream defOut =compressor.apply(bos)){

            byte[] buffer = new byte[Constants.BUFFER_READING_SIZE];
            int size = 0;
            while (-1 != (size = in.read(buffer))) {
                defOut.write(buffer, 0, size);
            }
            defOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AlgorithmException(e.getMessage());
        }
    }
    public void genericDecompress(FunctionException<BufferedInputStream, CompressorInputStream, IOException> decompressor) throws AlgorithmException {
        createFolderIfNotExists(Paths.get(dataInfo.getDecompressPath() + "\\" + dataInfo.getDecompressFileName()));
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream( Paths.get(dataInfo.getPath() + "\\" + dataInfo.getFileName())));
             OutputStream out = Files.newOutputStream(Paths.get(dataInfo.getDecompressPath() + "\\" + dataInfo.getDecompressFileName()));
             CompressorInputStream defIn =decompressor.apply(in)) {

            final byte[] buffer = new byte[Constants.BUFFER_READING_SIZE];
            int n = 0;
            while (-1 != (n = defIn.read(buffer))) {
                out.write(buffer, 0, n);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AlgorithmException(e.getMessage());
        }
    }

    private void createFolderIfNotExists(Path path) throws AlgorithmException {

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
            throw new AlgorithmException("Directory could not be created for the designed path: " + path.getParent());
        }
    }
}
