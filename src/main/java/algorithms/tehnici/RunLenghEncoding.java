package algorithms.tehnici;

import algorithms.AbstractAlgoritm;
import config.Constants;
import domain.CharArray;
import domain.DataInfo;
import exceptions.AlgorithmException;

/**
 * O metoda primitiva de a scurta mesajele text, fara a avea numere an continutul acestora.
 */
public class RunLenghEncoding extends AbstractAlgoritm {

    public static final Character escapeChar = '|';

    private int lastApparition = 0;
    public char lastC = 0;
    private boolean isEscape = false;

    @Override
    public DataInfo compress() throws AlgorithmException {
        dataInfo.setCompressionStart();
        reading(this::compression);
        dataInfo.setCompressionStop();
        return this.dataInfo;
    }

    public void compression(char[] datas, int size) throws AlgorithmException {
        char c = cleanFirstTime ? datas[0] : lastC;
        int apparitions = cleanFirstTime ? 0 : lastApparition;

        CharArray compress = new CharArray();
        for (int i = 0; i < size; i++) {
            if (c != datas[i]) {
                compress.add(apparitions);
                if (Character.isDigit(c) || escapeChar.equals(c)) {
                    compress.add(escapeChar);
                }
                compress.add(c);
                c = datas[i];
                apparitions = 1;
            } else {
                apparitions++;
            }
        }

        lastApparition = apparitions;
        lastC = c;

        if (size == 0) {
            compress.add(apparitions);
            if (Character.isDigit(c) || escapeChar.equals(c)) {
                compress.add(escapeChar);
            }
            compress.add(c);
        }
        writing(compress.getChars(), true);
    }

    @Override
    public DataInfo decompress() throws AlgorithmException {
        dataInfo.setDecompressionStart();
        reading((datas, size) -> {
           char[] arr= decompression(datas,size);
            writing(arr, false);
        });
        dataInfo.setDecompressionStop();

        return this.dataInfo;
    }

    public char[] decompression(char[] datas, int size) throws AlgorithmException {
        int apparitions = cleanFirstTime ? 0 : lastApparition;
        CharArray decompress = new CharArray();
        for (int i = 0; i < size; i++) {
            if (Character.isDigit(datas[i]) && !isEscape) {
                apparitions *= 10;
                apparitions += Character.getNumericValue(datas[i]);
            } else {
                if (escapeChar.equals(datas[i]) && !isEscape) {
                    i++;
                    isEscape = true;
                    if (i == datas.length) {
                        break;
                    }
                }
                if (decompress.getSize() >= Constants.BUFFER_READING_SIZE) {
                    writing(decompress.getChars(), false);
                    decompress.reset();
                }
                decompress.add(datas[i], apparitions);
                apparitions = 0;
                isEscape = false;
            }
        }
        lastApparition = apparitions;
        return decompress.getChars();
    }
    @Override
    public String getAlgorithmName() {
        return "Run-Lengh Encoding";
    }

    @Override
    public String getAlgorithmKey() {
        return "RLE";
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
