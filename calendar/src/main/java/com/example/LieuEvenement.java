package com.example;

public record LieuEvenement(String value) {
    public LieuEvenement { if (value == null) throw new IllegalArgumentException(); }
}