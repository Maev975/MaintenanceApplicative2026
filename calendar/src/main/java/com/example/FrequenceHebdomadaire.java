package com.example;

import java.time.LocalDateTime;

public record FrequenceHebdomadaire() implements Frequence {
    @Override
    public LocalDateTime prochaine(LocalDateTime depuis) {
        return depuis.plusWeeks(1);
    }

    @Override
    public String descriptionPeriode() {
        return "chaque semaine";
    }
}
