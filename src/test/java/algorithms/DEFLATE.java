package algorithms;

import config.Constant;
import exceptions.AlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class DEFLATE {
    private Algorithm alg;
    private static final String INPUT_PATH = "\\algorithms\\input\\";
    @BeforeEach
    void setUp() {
        alg = new algorithms.slidingWindow.DEFLATE();
    }

    @Test
    @DisplayName("RLE: test compress binary file")
    void binaryTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg, new File(Constant.TEST_PATH + INPUT_PATH + "vlc-3.0.17.4-win64.exe"));
    }
}
