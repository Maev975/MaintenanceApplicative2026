package com.example;

import java.time.LocalDateTime;

public record EventPeriodique(
    EventId id,
    TitreEvenement title,
    DateEvenement dateDebut,
    HeureDebut heureDebut,
    DureeEvenement duree,
    Frequence frequence
) implements Event {

    @Override public LocalDateTime getDebut() { return LocalDateTime.of(dateDebut.value(), heureDebut.value()); }
    @Override public LocalDateTime getFin() { return getDebut().plusMinutes(duree.value()); }

    @Override
    public boolean estDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        LocalDateTime occ = getDebut();
        while (!occ.isAfter(fin)) {
            if (!occ.isBefore(debut) && !occ.isAfter(fin)) return true;
            occ = frequence.prochaine(occ);
        }
        return false;
    }

    @Override
    public boolean estEnConflitAvec(Event autre) {
        return this.getDebut().isBefore(autre.getFin()) && this.getFin().isAfter(autre.getDebut());
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.value() + " " + frequence.descriptionPeriode();
    }
}