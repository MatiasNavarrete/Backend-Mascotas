package com.example.mascota.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetStatusTest {

    @Test
    @DisplayName("Debería cubrir los métodos internos del Enum PetStatus")
    void testEnumMethods() {
        PetStatus[] statuses = PetStatus.values();
        assertNotNull(statuses);
        assertEquals(4, statuses.length); // Tienes 4 estados: LOST, FOUND, SIGHTED, RECOVERED

        PetStatus lostStatus = PetStatus.valueOf("LOST");
        assertEquals(PetStatus.LOST, lostStatus);

        PetStatus recoveredStatus = PetStatus.valueOf("RECOVERED");
        assertEquals(PetStatus.RECOVERED, recoveredStatus);
    }
}