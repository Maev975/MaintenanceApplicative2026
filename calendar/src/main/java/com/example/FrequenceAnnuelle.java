package com.example;

import java.time.LocalDateTime;

public record FrequenceAnnuelle() implements Frequence {
    @Override
    public LocalDateTime prochaine(LocalDateTime depuis) {
        return depuis.plusYears(1);
    }

    @Override
    public String descriptionPeriode() {
        return "chaque année";
    }
}
