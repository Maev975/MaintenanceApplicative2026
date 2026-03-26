package com.example;

public record EventPeriodique(TitreEvenement title, FrequenceEvenement frequence) implements Event {
    @Override
    public String description() {
        return "Événement périodique : " + title.value() + " tous les " + frequence.value() + " jours";
    }
}