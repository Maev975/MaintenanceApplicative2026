package com.example;

import java.time.LocalDateTime;

public record FrequenceEvenement(int value) implements Frequence {
    public FrequenceEvenement {
        if (value <= 0)
            throw new IllegalArgumentException("La fréquence doit être positive.");
    }

    @Override
    public LocalDateTime prochaine(LocalDateTime depuis) {
        return depuis.plusDays(value);
    }

    @Override
    public String descriptionPeriode() {
        return "tous les " + value + " jours";
    }
}