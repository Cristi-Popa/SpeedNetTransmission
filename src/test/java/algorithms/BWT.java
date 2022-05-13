package algorithms;

import algorithms.tehnici.BurrowsWheelerTransform;
import algorithms.tehnici.RunLenghEncoding;
import config.Constant;
import exceptions.AlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class BWT {

    private Algorithm alg;
    private static final String INPUT_PATH = "\\algorithms\\input\\";
    private static final String EXPECT_PATH = "\\algorithms\\expected\\";

    @BeforeEach
    void setUp() {
        alg = new BurrowsWheelerTransform();
    }

    @Test
    @DisplayName("BWT: test compress only letters")
    void clasicTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg,
                new File(Constant.TEST_PATH + INPUT_PATH + "clasicBWT.txt"),
                new File(Constant.TEST_PATH + EXPECT_PATH + "clasicBWT.txt"));
    }

    @Test
    @DisplayName("BWT: test compress only letters and numbers")
    void numberTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg,
                new File(Constant.TEST_PATH + INPUT_PATH + "numberBWT.txt"),
                new File(Constant.TEST_PATH + EXPECT_PATH + "numberBWT.txt"));
    }

    @Test
    @DisplayName("BWT: test compress only letters and escape characters")
    void escapeTest() throws AlgorithmException, IOException {
        Utils.testFileMismach(alg,
                new File(Constant.TEST_PATH + INPUT_PATH + "escapeBWT.txt"),
                new File(Constant.TEST_PATH + EXPECT_PATH + "escapeBWT.txt"));
    }
}
