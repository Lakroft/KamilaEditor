package com.lakroft;

public class CharEntity implements Comparable<CharEntity> {
    private final char nextChar;
    private int freq = 0;

    public CharEntity(char nextChar) {
        this.nextChar = nextChar;
    }

    public void increment() {
        freq++;
    }

    public char getNextChar() {
        return nextChar;
    }

    public int getFreq() {
        return freq;
    }

    @Override
    public int compareTo(CharEntity other) {
        return Integer.compare(getFreq(), other.getFreq());
    }
}
