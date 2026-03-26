package com.example;

public record Reunion(TitreEvenement title, LieuEvenement lieu, ParticipantEvenement participants) implements Event {
    @Override
    public String description() {
        return "Réunion : " + title.value() + " à " + lieu.value() + " avec " + participants.value();
    }
}