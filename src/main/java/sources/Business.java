package sources;

import algorithms.Algorithm;
import algorithms.nondictionary.BZIP2;
import algorithms.slidingWindow.DEFLATE;
import algorithms.slidingWindow.LZMA;
import algorithms.tehnici.RunLenghEncoding;
import config.Prop;
import domain.DataInfo;
import exceptions.AlgorithmException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Business {

//    private String filename = "\\RLE.txt";

    private List<Algorithm> algorithms = new ArrayList<>() {{
        add(new RunLenghEncoding().setFileName(new File(Prop.INPUT_FILE_PATH + "\\SampleCSVFile_53000kb.csv")));
//        add(new BurrowsWheelerTransform().setFileName(new File(Properties.INPUT_FILE_PATH + "\\SampleCSVFile_53000kb.csv")));

        add(new DEFLATE().setFileName(new File(Prop.INPUT_FILE_PATH + "\\SampleCSVFile_53000kb.csv")));
        add(new LZMA().setFileName(new File(Prop.INPUT_FILE_PATH + "\\SampleCSVFile_53000kb.csv")));

        add(new BZIP2().setFileName(new File(Prop.INPUT_FILE_PATH + "\\SampleCSVFile_53000kb.csv")));
    }};

    public void execTest() {
        algorithms.forEach(a -> {
            try {
                DataInfo info = a.exec();
                System.out.println(a.getAlgorithmName() + "("+a.getAlgorithmKey()+") compression time = " + info.getCompressionDuration() + ", file size =" + info.getCompressedSize());
                System.out.println(a.getAlgorithmName() + "("+a.getAlgorithmKey()+") decompression time = " + info.getDecompressionDuration() + ", file size =" + info.getDecompressedSize());
                System.out.println("Rata de compresie: "+info.getCompressionRate()+"%");

            } catch (AlgorithmException e) {
                e.printStackTrace();
            }
        });

//        Helper.createBinaryFileFromTxt(new File(Properties.INPUT_FILE_PATH + "\\RLE.txt"));
    }
}
