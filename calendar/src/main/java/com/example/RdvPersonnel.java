package com.example;

public record RdvPersonnel(TitreEvenement title, DateEvenement dateDebut) implements Event {
    @Override
    public String description() {
        return "RDV : " + title.value() + " à " + dateDebut.value().toString();
    }
}