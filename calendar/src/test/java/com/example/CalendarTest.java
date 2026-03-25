package com.example;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class CalendarTest {
    @Test
    public void testAjouterEvent() {
        CalendarManager cm = new CalendarManager();
        cm.ajouterEvent("RDV_PERSONNEL", "Piscine", "Max", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);
        assertEquals(1, cm.events.size());
        assertEquals("RDV : Piscine à 2024-06-01T10:00", cm.events.get(0).description());
    }

   @Test
   public void testEventsDansPeriode() {
         CalendarManager cm = new CalendarManager();
         cm.ajouterEvent("RDV_PERSONNEL", "Piscine", "Max", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);
         cm.ajouterEvent("PERIODIQUE", "Gym", "Bob", LocalDateTime.of(2024, 6, 1, 8, 0), 60, null, null, 2);

    
         assertEquals(1, cm.eventsDansPeriode(LocalDateTime.of(2024, 6, 1, 9, 0), LocalDateTime.of(2024, 6, 1, 11, 0)).size());
         assertEquals(2, cm.eventsDansPeriode(LocalDateTime.of(2024, 6, 1, 7, 0), LocalDateTime.of(2024, 6, 3, 9, 0)).size());

    }

    @Test
    public void testDateDebutAvantPeriode() {
        CalendarManager cm = new CalendarManager();
        cm.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice", LocalDateTime.of(2024, 6, 2, 14, 0), 30, null, null, 0);
        cm.ajouterEvent("RDV_PERSONNEL", "Coiffeur", "Bob", LocalDateTime.of(2024, 6, 3, 16, 0), 45, null, null, 0);

        assertEquals(1, cm.eventsDansPeriode(LocalDateTime.of(2024, 6, 2, 13, 0), LocalDateTime.of(2024, 6, 2, 15, 0)).size());
        assertEquals(1, cm.eventsDansPeriode(LocalDateTime.of(2024, 6, 3, 15, 0), LocalDateTime.of(2024, 6, 3, 17, 0)).size());
    }

    @Test
    public void testConflit() {
        CalendarManager cm = new CalendarManager();
        Event e1 = new Event("RDV_PERSONNEL", "Dentiste", "Alice", LocalDateTime.of(2024, 6, 2, 14, 0), 30, null, null, 0);
        Event e2 = new Event("RDV_PERSONNEL", "Coiffeur", "Bob", LocalDateTime.of(2024, 6, 2, 14, 15), 45, null, null, 0);

        assertEquals(true, cm.conflit(e1, e2));

    }

    @Test
    public void testConflitMemeType() {
        CalendarManager cm = new CalendarManager();
        Event e1 = new Event("PERIODIQUE", "Dentiste", "Alice", LocalDateTime.of(2024, 6, 2, 14, 0), 30, null, null, 0);
        Event e2 = new Event("PERIODIQUE", "Coiffeur", "Bob", LocalDateTime.of(2024, 6, 2, 14, 30), 45, null, null, 0);

        assertEquals(false, cm.conflit(e1, e2));
    }

    @Test
    public void testConflit_TotalementVrai() {
        CalendarManager cm = new CalendarManager();
        Event e1 = new Event("RDV", "A", "A", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);
        Event e2 = new Event("RDV", "B", "B", LocalDateTime.of(2024, 6, 1, 10, 30), 60, null, null, 0);

        assertTrue(cm.conflit(e1, e2)); 
    }
    
    @Test
    public void testConflit_PremierFaux() {
        CalendarManager cm = new CalendarManager();
        Event e1 = new Event("RDV", "A", "A", LocalDateTime.of(2024, 6, 1, 15, 0), 60, null, null, 0);
        Event e2 = new Event("RDV", "B", "B", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);

        assertFalse(cm.conflit(e1, e2));
    }

    @Test
    public void testConflit_SecondFaux() {
        CalendarManager cm = new CalendarManager();

        Event e1 = new Event("RDV", "A", "A", LocalDateTime.of(2024, 6, 1, 8, 0), 60, null, null, 0);
        Event e2 = new Event("RDV", "B", "B", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);
        assertFalse(cm.conflit(e1, e2));
    }

    @Test
    public void testAfficherEvenements() {
        CalendarManager cm = new CalendarManager();
        cm.ajouterEvent("RDV_PERSONNEL", "Piscine", "Max", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);
        cm.ajouterEvent("REUNION", "Projet X", "Alice", LocalDateTime.of(2024, 6, 2, 14, 0), 120, "Salle A", "Bob,Charlie", 0);
        cm.ajouterEvent("PERIODIQUE", "Gym", "Bob", LocalDateTime.of(2024, 6, 1, 8, 0), 60, null, null, 2);

        cm.afficherEvenements();
    }

}
