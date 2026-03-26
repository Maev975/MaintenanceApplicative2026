package com.example;

import java.time.LocalTime;

public record HeureDebut(LocalTime value) {
    public HeureDebut {
        if (value == null) throw new IllegalArgumentException("L'heure de début ne peut pas être nulle.");
    }
}
