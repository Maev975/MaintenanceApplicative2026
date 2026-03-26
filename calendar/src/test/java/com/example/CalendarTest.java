package com.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class CalendarTest {
// ***************************************************** Tests CalendarManager.java *****************************************************

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
    public void testConflit_UnPeriodiqueAvecNonPeriodique() {
        CalendarManager cm = new CalendarManager();
        Event ePeriodique = new Event("PERIODIQUE", "Gym", "Bob", LocalDateTime.of(2024, 6, 1, 8, 0), 60, null, null, 2);
        Event eNormal = new Event("RDV", "Rdv", "A", LocalDateTime.of(2024, 6, 1, 8, 30), 30, null, null, 0);

        assertFalse(cm.conflit(ePeriodique, eNormal));
        assertFalse(cm.conflit(eNormal, ePeriodique));
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

// ***************************************************** Tests Event.java *****************************************************
    
    @Test
    public void testAfficherEvenements() {
        CalendarManager cm = new CalendarManager();
        cm.ajouterEvent("RDV_PERSONNEL", "Piscine", "Max", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);
        cm.ajouterEvent("REUNION", "Projet X", "Alice", LocalDateTime.of(2024, 6, 2, 14, 0), 120, "Salle A", "Bob,Charlie", 0);
        cm.ajouterEvent("PERIODIQUE", "Gym", "Bob", LocalDateTime.of(2024, 6, 1, 8, 0), 60, null, null, 2);

        cm.afficherEvenements();
    }

    @Test 
    public void testDescription() {
        Event e1 = new Event("RDV_PERSONNEL", "Piscine", "Max", LocalDateTime.of(2024, 6, 1, 10, 0), 60, null, null, 0);
        Event e2 = new Event("REUNION", "Projet X", "Alice", LocalDateTime.of(2024, 6, 2, 14, 0), 120, "Salle A", "Bob,Charlie", 0);
        Event e3 = new Event("PERIODIQUE", "Gym", "Bob", LocalDateTime.of(2024, 6, 1, 8, 0), 60, null, null, 2);

        assertEquals("RDV : Piscine à 2024-06-01T10:00", e1.description());
        assertEquals("Réunion : Projet X à Salle A avec Bob,Charlie", e2.description());
        assertEquals("Événement périodique : Gym tous les 2 jours", e3.description());
    }

    @Test
    public void testDescriptionTypeInconnu() {
    Event eInconnu = new Event("AUTRE", "Test", "Max", LocalDateTime.of(2024, 6, 1, 10, 0), 30, null, null, 0);
    assertEquals("", eInconnu.description(), "La description doit être vide pour un type inconnu");
    }

// ***************************************************** Tests Main.java *****************************************************

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private String runMainWithInputExpectingNoSuchElement(String input) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, "UTF-8");
        System.setIn(in);
        System.setOut(ps);
        try {
            assertThrows(NoSuchElementException.class, () -> Main.main(new String[0]));
        } finally {
            System.setIn(systemIn);
            System.setOut(systemOut);
        }
        return baos.toString(StandardCharsets.UTF_8.name());
    }

    @Test
    public void testRogerLoginAndViewAllEvents() throws Exception {
        String input = String.join("\n",
                "1",
                "Roger",
                "Chat",
                "1",
                "1",
                "5",
                "non"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Bonjour, Roger"));
        assertTrue(out.contains("=== Menu Gestionnaire d'Événements ==="));
    }

    @Test
    public void testCreateAccountSuccess() throws Exception {
        String input = String.join("\n",
                "2",
                "Jean",
                "pwd123",
                "pwd123",
                "5",
                "non"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Bonjour, Jean"));
    }

    @Test
    public void testCreateAccountMismatch() throws Exception {
        String input = String.join("\n",
                "2",
                "Luc",
                "a",
                "b"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Les mots de passes ne correspondent pas..."));
    }

    @Test
    public void testAddPersonalEvent() throws Exception {
        String input = String.join("\n",
                "1",
                "Roger",
                "Chat",
                "2",
                "Mon RDV",
                "2026",
                "1",
                "4",
                "9",
                "30",
                "45",
                "5",
                "non"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Événement ajouté."));
    }

    @Test
    public void testAddMeetingWithParticipants() throws Exception {
        String input = String.join("\n",
                "1",
                "Roger",
                "Chat",
                "3",
                "Team meeting",
                "2026",
                "1",
                "5",
                "10",
                "0",
                "60",
                "Salle A",
                "oui",
                "Alice",
                "non",
                "5",
                "non"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Événement ajouté."));
    }

    @Test
    public void testAddPeriodicEvent() throws Exception {
        String input = String.join("\n",
                "1",
                "Roger",
                "Chat",
                "4",
                "Yoga",
                "2026",
                "1",
                "6",
                "7",
                "0",
                "7",
                "5",
                "non"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Événement ajouté."));
    }

    @Test
    public void testPierreLoginSuccess() throws Exception {
        String input = String.join("\n",
                "1",
                "Pierre",
                "KiRouhl",
                "5",
                "non"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Bonjour, Pierre"));
    }

    @Test
    public void testAfficherListeNonEmptyViaMonthView() throws Exception {
        String input = String.join("\n",
                "1",          
                "Roger",
                "Chat",
                "2",
                "RDV Test",
                "2026",
                "7",
                "10",
                "9",
                "0",
                "60",
                "1",
                "2",
                "2026",
                "7",
                "5",
                "non"
        ) + "\n";

        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Événements trouvés :") || out.contains("- RDV"));
    }

    @Test
    public void testPierreWrongPassword() throws Exception {
        String input = String.join("\n",
                "1",
                "Pierre",
                "badpass"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertFalse(out.contains("Bonjour, Pierre"));
    }

    @Test
    public void testViewAllEventsSubcase() throws Exception {
        String input = String.join("\n",
                "1",
                "Roger",
                "Chat",
                "1",
                "1",
                "5",
                "non"
        ) + "\n";
        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("=== Menu de visualisation d'Événements ===") );
    }

    @Test
    public void testLoginWithCreatedUser() throws Exception {
        String input = String.join("\n",
            "2",
            "Jean",
            "pwd123",
            "pwd123",
            "5",
            "oui",
            "1",
            "Jean",
            "pwd123",
            "5",
            "non"
        ) + "\n";

        String out = runMainWithInputExpectingNoSuchElement(input);
        assertTrue(out.contains("Bonjour, Jean"));
    }

    @Test
    public void testRogerWrongPassword() throws Exception {
        String input = String.join("\n",
            "1",
            "Roger",
            "WrongPass"
        ) + "\n";

        String out = runMainWithInputExpectingNoSuchElement(input);
        assertFalse(out.contains("Bonjour, Roger"));
    }

    @Test
    public void testViewMonthWeekDay_NoEvents() throws Exception {
        String inMonth = String.join("\n",
            "1",
            "Roger",
            "Chat",
            "1",
            "2",
            "2026",
            "12",
            "5",
            "non"
        ) + "\n";
        String outMonth = runMainWithInputExpectingNoSuchElement(inMonth);
        assertTrue(outMonth.contains("Aucun événement trouvé pour cette période."));

        String inWeek = String.join("\n",
            "1",
            "Roger",
            "Chat",
            "1",
            "3",
            "2026",
            "12",
            "5",
            "non"
        ) + "\n";
        String outWeek = runMainWithInputExpectingNoSuchElement(inWeek);
        assertTrue(outWeek.contains("Aucun événement trouvé pour cette période."));

        String inDay = String.join("\n",
            "1",
            "Roger",
            "Chat",
            "1",
            "4",
            "2026",
            "12",
            "31",
            "5",
            "non"
        ) + "\n";
        String outDay = runMainWithInputExpectingNoSuchElement(inDay);
        assertTrue(outDay.contains("Aucun événement trouvé pour cette période."));
    }

 
  
  
   



}
