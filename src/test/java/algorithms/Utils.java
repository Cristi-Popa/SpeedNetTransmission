package algorithms;

import domain.DataInfo;
import exceptions.AlgorithmException;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {

    public static void testFileMismach(Algorithm alg, File inputFile, File expectCompress) throws AlgorithmException, IOException {
        alg.setFileName(inputFile);
        DataInfo info = alg.exec();

        Assertions.assertEquals(inputFile.length(), info.getDecompressedSize());
        Assertions.assertEquals(-1, Files.mismatch(inputFile.toPath(), new File(info.getDecompressPath() + "\\" + info.getDecompressFileName()).toPath()));

        Assertions.assertEquals(expectCompress.length(), info.getCompressedSize());
        Assertions.assertEquals(-1, Files.mismatch(expectCompress.toPath(), new File(info.getCompressPath() + "\\" + info.getCompressFileName()).toPath()));
    }

    public static void testFileMismach(Algorithm alg, File inputFile) throws AlgorithmException, IOException {
        alg.setFileName(inputFile);
        DataInfo info = alg.exec();

        Assertions.assertEquals(inputFile.length(), info.getDecompressedSize());
        Assertions.assertEquals(-1, Files.mismatch(inputFile.toPath(), new File(info.getDecompressPath() + "\\" + info.getDecompressFileName()).toPath()));
    }
}
