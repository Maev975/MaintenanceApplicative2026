package com.example;

import java.time.LocalDateTime;

public record EventPeriodique(EventId id, TitreEvenement title, DateEvenement dateDebut, FrequenceEvenement frequence) implements Event {
    
    @Override public LocalDateTime getDebut() { return dateDebut.value(); }
    @Override public LocalDateTime getFin() { return dateDebut.value().plusHours(1); }

    @Override
    public boolean estDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        LocalDateTime occ = getDebut();
        while (!occ.isAfter(fin)) {
            if (!occ.isBefore(debut) && !occ.isAfter(fin)) return true;
            occ = occ.plusDays(frequence.value());
        }
        return false;
    }

    @Override
    public boolean estEnConflitAvec(Event autre) {
        return this.getDebut().isBefore(autre.getFin()) && this.getFin().isAfter(autre.getDebut());
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.value() + " tous les " + frequence.value() + " jours";
    }
}