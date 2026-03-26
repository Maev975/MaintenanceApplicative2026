package com.example;

import java.time.LocalDate;

public record DateEvenement(LocalDate value) {
    public DateEvenement {
        if (value == null) throw new IllegalArgumentException("La date ne peut pas être nulle.");
    }
}
