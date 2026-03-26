package com.example;

import java.time.LocalDateTime;

public interface Event {
    EventId id();
    String description();
    
    boolean estDansPeriode(LocalDateTime debut, LocalDateTime fin);
    boolean estEnConflitAvec(Event autre);
    
    LocalDateTime getDebut();
    LocalDateTime getFin();
}