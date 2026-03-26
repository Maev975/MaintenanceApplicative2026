package com.example;

public record DateEvenement(java.time.LocalDateTime value) {
    public DateEvenement { if (value == null) throw new IllegalArgumentException(); }
}
