package algorithms;

import algorithms.tehnici.RunLenghEncoding;
import config.Constant;
import exceptions.AlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class RLE {

    private Algorithm alg;
    private static final String INPUT_PATH = "\\algorithms\\input\\";
    private static final String EXPECT_PATH = "\\algorithms\\expected\\";

    @BeforeEach
    void setUp() {
        alg = new RunLenghEncoding();
    }

    @Test
    @DisplayName("RLE: test compress only letters")
    void clasicTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg,
                new File(Constant.TEST_PATH + INPUT_PATH + "clasicRLE.txt"),
                new File(Constant.TEST_PATH + EXPECT_PATH + "clasicRLE.txt"));
    }

    @Test
    @DisplayName("RLE: test compress only letters and numbers")
    void numberTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg,
                new File(Constant.TEST_PATH + INPUT_PATH + "numberRLE.txt"),
                new File(Constant.TEST_PATH + EXPECT_PATH + "numberRLE.txt"));
    }

    @Test
    @DisplayName("RLE: test compress only letters and escape characters")
    void escapeTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg,
                new File(Constant.TEST_PATH + INPUT_PATH + "escapeRLE.txt"),
                new File(Constant.TEST_PATH + EXPECT_PATH + "escapeRLE.txt"));
    }

    @Test
    @DisplayName("RLE: test compress binary file")
    void binaryTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg,
                new File(Constant.TEST_PATH + INPUT_PATH + "bRLE.txt"),
                new File(Constant.TEST_PATH + EXPECT_PATH + "bRLE.txt")
                );
    }


}
