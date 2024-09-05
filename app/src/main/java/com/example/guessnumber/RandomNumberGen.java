package com.example.guessnumber;

import java.util.Random;

public class RandomNumberGen {
    // start and end are fixed
    private final int start;
    private final int end;
    private final Random random;

    // Constructor to initialize the interval and Random object
    public RandomNumberGen(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must be less than or equal to End");
        }
        this.start = start;
        this.end = end;
        this.random = new Random();
    }

    // Method to generate a random number within the interval [start, end]
    public int generate() {
        return random.nextInt((end - start) + 1) + start;
    }
}
