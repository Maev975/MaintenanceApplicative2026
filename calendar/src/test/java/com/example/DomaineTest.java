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
    

}
