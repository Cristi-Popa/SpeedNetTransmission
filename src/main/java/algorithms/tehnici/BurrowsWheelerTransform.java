package algorithms.tehnici;

import algorithms.AbstractAlgoritm;
import domain.CharArray;
import domain.DataInfo;
import exceptions.AlgorithmException;

import java.util.*;
import java.util.stream.IntStream;


public class BurrowsWheelerTransform extends AbstractAlgoritm {

    private RunLenghEncoding rle = new RunLenghEncoding();

    public BurrowsWheelerTransform() {
        this.rle.setDataInfo(dataInfo);
    }

    @Override
    public DataInfo compress() throws AlgorithmException {
        dataInfo.setCompressionStart();
        reading((datas, size) -> {
            if (size != 0) {
                List<Integer> list = new ArrayList<>(Arrays.stream(IntStream.range(0, size).toArray()).boxed().toList());

                list.sort((x, y) -> {
                    char[] r1 = rotatePosition(datas, size, x);
                    char[] r2 = rotatePosition(datas, size, y);
                    return Arrays.compare(r1, r2);
                });
                char[] rez = new char[size];
                int key = -1;
                for (int i = 0; i < list.size(); i++) {
                    rez[i] = datas[Math.floorMod(list.get(i) - 1, size)];
                    if (list.get(i) == 0) {
                        key = i + 1;
                    }
                }
                CharArray compress = new CharArray();
                compress.add(key);
                compress.add(RunLenghEncoding.escapeChar);
                writing(compress.getChars(), true);
                rle.lastC = datas[0];
                rle.cleanFirstTime = false;
                rle.compression(rez, rez.length);
            } else
                rle.compression(datas, size);
        });
        dataInfo.setCompressionStop();
        return dataInfo;
    }

    @Override
    public DataInfo decompress() throws AlgorithmException {
        dataInfo.setDecompressionStart();
        reading((datas, size) -> {
            if (size!= 0) {
                int key = 0;
                int i = 0;
                while (!RunLenghEncoding.escapeChar.equals(datas[i])) {
                    key *= 10;
                    key += Character.getNumericValue(datas[i]);
                    i++;
                }
                i++;
                char[] data = new char[size - i];
                System.arraycopy(datas, i, data, 0, data.length);
                rle.cleanFirstTime = true;
                char[] decompress = rle.decompression(data, data.length);
                data = new char[decompress.length];
                List<Integer> sortPattern = new ArrayList<>(Arrays.stream(IntStream.range(0, decompress.length).toArray()).boxed().toList());
                sortPattern.sort(Comparator.comparingInt(x -> decompress[x]));

                List<Integer> workingList = new ArrayList<>(sortPattern);
                for (i = 0; i < decompress.length - 1; i++) {
                    data[i] = decompress[workingList.get(key-1)];
                    List<Integer> aux = new ArrayList<>(sortPattern);
                    for (int j = 0; j < aux.size(); j++) {
                        aux.set(j, workingList.get(sortPattern.get(j)));
                    }
                    workingList = aux;
                }
                data[decompress.length - 1] = decompress[workingList.get(key-1)];

                writing(data, false);
            }
            writing(new char[]{}, false);
        });
        dataInfo.setDecompressionStop();
        return this.dataInfo;
    }

    @Override
    public String getAlgorithmName() {
        return "Burrows-Wheeler Transform";
    }

    @Override
    public String getAlgorithmKey() {
        return "BWT";
    }

    @Override
    public boolean isForText() {
        return true;
    }

    @Override
    public boolean isForBinary() {
        return false;
    }

    public static char[] rotatePosition(char[] arr, int size, int position) {
        char[] rez = new char[size];
        System.arraycopy(arr, position, rez, 0, size - position);
        System.arraycopy(arr, 0, rez, size - position, position);
        return rez;
    }
}
