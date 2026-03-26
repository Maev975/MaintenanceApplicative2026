package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        java.time.LocalDateTime date = java.time.LocalDateTime.of(2024, 6, 1, 10, 0);
        DateEvenement dateEvenement = new DateEvenement(date);
        assertEquals(date, dateEvenement.value());
    }

    @Test
    void testDateEvenementNull() {
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(null));
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

}
