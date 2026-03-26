package com.example;

public record ParticipantEvenement(String value) {
    public ParticipantEvenement { if (value == null) throw new IllegalArgumentException(); }
}