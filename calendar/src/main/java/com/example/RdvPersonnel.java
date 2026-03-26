package com.example;

import java.time.LocalDateTime;

public record RdvPersonnel(EventId id, TitreEvenement title, DateEvenement dateDebut, DureeEvenement duree) implements Event {
    
    @Override public LocalDateTime getDebut() { return dateDebut.value(); }
    @Override public LocalDateTime getFin() { return dateDebut.value().plusMinutes(duree.value()); }

    @Override
    public boolean estDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return !getDebut().isBefore(debut) && !getDebut().isAfter(fin);
    }

    @Override
    public boolean estEnConflitAvec(Event autre) {
        return this.getDebut().isBefore(autre.getFin()) && this.getFin().isAfter(autre.getDebut());
    }

    @Override public String description() { return "RDV : " + title.value() + " à " + dateDebut.value().toString(); }
}