package com.example;

import java.util.concurrent.atomic.AtomicLong;

public record EventId(long value) {
    private static final AtomicLong COUNTER = new AtomicLong(1);

    public EventId {
        // long primitive can't be null, but we validate positive
        if (value <= 0) throw new IllegalArgumentException("L'identifiant doit être positif");
    }

    // Méthode utilitaire pour générer un ID simple incrémental
    public static EventId generate() {
        return new EventId(COUNTER.getAndIncrement());
    }

    public static EventId fromString(String s) {
        if (s == null) throw new IllegalArgumentException("ID null");
        try {
            long v = Long.parseLong(s);
            return new EventId(v);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("ID invalide", ex);
        }
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}