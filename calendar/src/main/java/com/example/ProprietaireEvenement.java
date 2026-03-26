package com.example;

public record ProprietaireEvenement(String value) {
    public ProprietaireEvenement { if (value == null || value.isBlank()) throw new IllegalArgumentException(); }
}