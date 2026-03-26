package com.example;

public record DureeEvenement(int value) {
    public DureeEvenement { if (value < 0) throw new IllegalArgumentException(); }
}
