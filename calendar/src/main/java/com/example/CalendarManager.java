package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    public List<Event> events = new ArrayList<>();

    public void ajouter(Event e) {
        events.add(e);
    }

    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return events.stream()
                     .filter(e -> e.estDansPeriode(debut, fin))
                     .toList();
    }

    public boolean conflit(Event e1, Event e2) {
        return e1.estEnConflitAvec(e2);
    }

    public void supprimerParId(EventId id) {
        events.removeIf(e -> e.id().equals(id));
    }

    public void afficherEvenements() {
        events.forEach(e -> System.out.println("- " + e.id().value() + " : " + e.description()));
    }

    public java.util.List<Event> getEvents() { return events; }

}