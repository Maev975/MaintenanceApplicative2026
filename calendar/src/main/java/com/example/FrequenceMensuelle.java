package com.example;

import java.time.LocalDateTime;

public record FrequenceMensuelle() implements Frequence {
    @Override
    public LocalDateTime prochaine(LocalDateTime depuis) {
        return depuis.plusMonths(1);
    }

    @Override
    public String descriptionPeriode() {
        return "chaque mois";
    }
}
