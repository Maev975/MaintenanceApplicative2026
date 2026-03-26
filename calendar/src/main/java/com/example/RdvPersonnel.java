package com.example;

import java.time.LocalDateTime;

public record RdvPersonnel(EventId id, TitreEvenement title, DateEvenement dateDebut, HeureDebut heureDebut, DureeEvenement duree) implements Event {

    @Override public LocalDateTime getDebut() { return LocalDateTime.of(dateDebut.value(), heureDebut.value()); }
    @Override public LocalDateTime getFin() { return getDebut().plusMinutes(duree.value()); }

    @Override
    public boolean estDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return !getDebut().isBefore(debut) && !getDebut().isAfter(fin);
    }

    @Override
    public boolean estEnConflitAvec(Event autre) {
        return this.getDebut().isBefore(autre.getFin()) && this.getFin().isAfter(autre.getDebut());
    }

    @Override public String description() { return "RDV : " + title.value() + " à " + getDebut(); }
}