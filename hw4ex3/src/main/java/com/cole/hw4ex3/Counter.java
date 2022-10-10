package com.cole.hw4ex3;

public class Counter {
    private int incrementor;

    public Counter() {
        incrementor = 0;
    }

    public int count() {
        incrementor++;
        return incrementor;
    }

    public int getIncrementor() {
        return incrementor;
    }
}
