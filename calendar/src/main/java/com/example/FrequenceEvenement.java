package com.example;

public record FrequenceEvenement(int value) {
    public FrequenceEvenement{ if (value < 0) throw new IllegalArgumentException(); }
}