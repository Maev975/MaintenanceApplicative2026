package com.example;

import java.time.LocalDateTime;

public interface Frequence {
    LocalDateTime prochaine(LocalDateTime depuis);
    /** Retourne la description de la période, ex: "chaque semaine" */
    String descriptionPeriode();
}
