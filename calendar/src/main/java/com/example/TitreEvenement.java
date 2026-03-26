package com.example;

public record TitreEvenement(String value) {
    public TitreEvenement {
        if (value == null || value.isBlank()) throw new IllegalArgumentException();
    }
}
