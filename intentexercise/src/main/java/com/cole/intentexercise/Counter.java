package com.cole.intentexercise;

public class Counter {
    private int incrementer;

    public Counter() {
        incrementer = 0;
    }

    public int count() {
        incrementer++;
        return incrementer;
    }

    public int getIncrementer() {
        return incrementer;
    }
}

