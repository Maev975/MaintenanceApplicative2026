package com.example;

import java.time.LocalDateTime;

public record Reunion(
    EventId id,
    TitreEvenement title,
    LieuEvenement lieu,
    ParticipantEvenement participants,
    DateEvenement dateDebut,
    HeureDebut heureDebut,
    DureeEvenement duree
) implements Event {

    @Override
    public LocalDateTime getDebut() {
        return LocalDateTime.of(dateDebut.value(), heureDebut.value());
    }

    @Override
    public LocalDateTime getFin() {
        return getDebut().plusMinutes(duree.value());
    }

    @Override
    public boolean estDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return !getDebut().isBefore(debut) && !getDebut().isAfter(fin);
    }

    @Override
    public boolean estEnConflitAvec(Event autre) {
        return this.getDebut().isBefore(autre.getFin()) && this.getFin().isAfter(autre.getDebut());
    }

    @Override
    public String description() {
        return "Réunion : " + title.value() + " à " + lieu.value() + " avec " + participants.value();
    }
}