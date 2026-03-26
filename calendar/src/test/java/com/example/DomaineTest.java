package com.example;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


public class DomaineTest {

    // ************************ Tests pour TitreEvenement ************************
    @Test
    void testTitreEvenement() {
        TitreEvenement titre = new TitreEvenement("Dentiste");
        assertEquals("Dentiste", titre.value());
    }

    @Test
    void testTitreEvenementNull() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(null));
    }

    @Test
    void testTitreEvenementBlank() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(""));
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement("   "));
    }

    // ************************ Tests pour ProprietaireEvenement ************************
    @Test
    void testProprietaire() {
        ProprietaireEvenement proprietaire = new ProprietaireEvenement("Alice");
        assertEquals("Alice", proprietaire.value());
    }

    @Test
    void testProprietaireNull() {
        assertThrows(IllegalArgumentException.class, () -> new ProprietaireEvenement(null));
    }

    @Test
    void testProprietaireBlank() {
        assertThrows(IllegalArgumentException.class, () -> new ProprietaireEvenement(""));
        assertThrows(IllegalArgumentException.class, () -> new ProprietaireEvenement("   "));
    }

    // ************************ Tests pour DateEvenement ************************
    @Test
    void testDateEvenement() {
        LocalDate date = LocalDate.of(2024, 6, 1);
        DateEvenement dateEvenement = new DateEvenement(date);
        assertEquals(date, dateEvenement.value());
    }

    @Test
    void testDateEvenementNull() {
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(null));
    }

    // ************************ Tests pour HeureDebut ************************
    @Test
    void testHeureDebut() {
        HeureDebut heure = new HeureDebut(LocalTime.of(10, 30));
        assertEquals(LocalTime.of(10, 30), heure.value());
    }

    @Test
    void testHeureDebutNull() {
        assertThrows(IllegalArgumentException.class, () -> new HeureDebut(null));
    }

    // ************************ Tests pour DureeEvenement ************************
    @Test
    void testDureeEvenement() {
        DureeEvenement duree = new DureeEvenement(60);
        assertEquals(60, duree.value());
    }

    @Test
    void testDureeEvenementNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DureeEvenement(-1));
    }

    // ************************ Tests pour LieuEvenement ************************
    @Test
    void testLieuEvenement() {
        LieuEvenement lieu = new LieuEvenement("Salle de réunion");
        assertEquals("Salle de réunion", lieu.value());
    }

    @Test
    void testLieuEvenementNull() {
        assertThrows(IllegalArgumentException.class, () -> new LieuEvenement(null));
    }

    // ************************ Tests pour ParticipantEvenement ************************
    @Test
    void testParticipantEvenement() {
        ParticipantEvenement participant = new ParticipantEvenement("Bob");
        assertEquals("Bob", participant.value());
    }

    @Test
    void testParticipantEvenementNull() {
        assertThrows(IllegalArgumentException.class, () -> new ParticipantEvenement(null));
    }

    // ************************ Tests pour Frequences polymorphiques ************************
    @Test
    void testFrequenceHebdomadaire() {
        FrequenceHebdomadaire f = new FrequenceHebdomadaire();
        assertEquals("chaque semaine", f.descriptionPeriode());
        var base = java.time.LocalDateTime.of(2024, 6, 1, 8, 0);
        assertEquals(base.plusWeeks(1), f.prochaine(base));
    }

    @Test
    void testFrequenceMensuelle() {
        FrequenceMensuelle f = new FrequenceMensuelle();
        assertEquals("chaque mois", f.descriptionPeriode());
        var base = java.time.LocalDateTime.of(2024, 6, 1, 8, 0);
        assertEquals(base.plusMonths(1), f.prochaine(base));
    }

    @Test
    void testFrequenceAnnuelle() {
        FrequenceAnnuelle f = new FrequenceAnnuelle();
        assertEquals("chaque année", f.descriptionPeriode());
        var base = java.time.LocalDateTime.of(2024, 6, 1, 8, 0);
        assertEquals(base.plusYears(1), f.prochaine(base));
    }

    // ************************ Tests pour Event ************************
    @Test
    void testDescriptionRdvPersonnel() {
        EventId id = EventId.generate();
        TitreEvenement titre = new TitreEvenement("Piscine");
        DateEvenement date = new DateEvenement(LocalDate.of(2024, 6, 1));
        HeureDebut heure = new HeureDebut(LocalTime.of(10, 0));

        Event rdv = new RdvPersonnel(id, titre, date, heure, new DureeEvenement(60));

        assertEquals(id, rdv.id());
        assertEquals("RDV : Piscine à 2024-06-01T10:00", rdv.description());
    }

    @Test
    void chaqueEvenementDoitAvoirUnIdUnique() {
        EventId id = EventId.generate();
        TitreEvenement titre = new TitreEvenement("Test ID");
        DateEvenement date = new DateEvenement(LocalDate.now());
        HeureDebut heure = new HeureDebut(LocalTime.of(9, 0));
        RdvPersonnel rdv = new RdvPersonnel(id, titre, date, heure, new DureeEvenement(30));

        assertEquals(id, rdv.id());
    }

    @Test
    void testSuppressionParId() {
        CalendarManager cm = new CalendarManager();
        EventId idASupprimer = EventId.generate();
        DateEvenement date = new DateEvenement(LocalDate.now());
        HeureDebut heure = new HeureDebut(LocalTime.of(9, 0));

        Event e1 = new RdvPersonnel(idASupprimer, new TitreEvenement("A supprimer"), date, heure, new DureeEvenement(30));
        Event e2 = new RdvPersonnel(EventId.generate(), new TitreEvenement("A garder"), date, heure, new DureeEvenement(30));

        cm.ajouter(e1);
        cm.ajouter(e2);

        cm.supprimerParId(idASupprimer);

        assertEquals(1, cm.getEvents().size());
        assertFalse(cm.getEvents().contains(e1));
    }

    @Test
    void testDescriptionEventPeriodique() {
        EventId id = EventId.generate();
        TitreEvenement titre = new TitreEvenement("Sport");
        DateEvenement date = new DateEvenement(LocalDate.of(2024, 6, 1));
        HeureDebut heure = new HeureDebut(LocalTime.of(8, 0));

        Event periodique = new EventPeriodique(id, titre, date, heure, new DureeEvenement(60), new FrequenceHebdomadaire());

        assertEquals("Événement périodique : Sport chaque semaine", periodique.description());
    }

    @Test
    void testDescriptionEventPeriodiqueMensuel() {
        EventId id = EventId.generate();
        Event periodique = new EventPeriodique(id, new TitreEvenement("Bilan"),
            new DateEvenement(LocalDate.of(2024, 6, 1)),
            new HeureDebut(LocalTime.of(9, 0)),
            new DureeEvenement(30),
            new FrequenceMensuelle());
        assertEquals("Événement périodique : Bilan chaque mois", periodique.description());
    }

    @Test
    void testDescriptionEventPeriodiqueAnnuel() {
        Event periodique = new EventPeriodique(EventId.generate(), new TitreEvenement("Anniversaire"),
            new DateEvenement(LocalDate.of(2024, 6, 1)),
            new HeureDebut(LocalTime.of(0, 0)),
            new DureeEvenement(60),
            new FrequenceAnnuelle());
        assertEquals("Événement périodique : Anniversaire chaque année", periodique.description());
    }
}
