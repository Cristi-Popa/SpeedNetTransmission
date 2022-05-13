package domain;

import config.Constants;

import java.util.ArrayList;
import java.util.Arrays;

public class CharArray {
    private ArrayList<char[]> chars = new ArrayList<>(Constants.BUFFER_READING_SIZE);
    private int size = 0;

    public char[] getChars() {
        char[] rez = new char[size];
        int position = 0;
        for (char[] c : chars) {
            System.arraycopy(c, 0, rez, position, c.length);
            position += c.length;
        }
        return rez;
    }

    public void add(char[] el) {
        chars.add(el);
        size += el.length;
    }

    public void add(char el) {
        chars.add(new char[]{el});
        size += 1;
    }

    public void add(long el) {
        char[] aux = String.valueOf(el).toCharArray();
        chars.add(aux);
        size += aux.length;
    }
    public void add(int el) {
        char[] aux = String.valueOf(el).toCharArray();
        chars.add(aux);
        size += aux.length;
    }

    public int getSize() {
        return size;
    }

    public void add(char data, int apparitions) {
        char[] aux = new char[apparitions];
        Arrays.fill(aux, data);
        chars.add(aux);
        size += apparitions;
    }

    public void reset() {
        size = 0;
        chars.clear();
    }
}
