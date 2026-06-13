package com.example.mascota.dto;

import com.example.mascota.enums.PetStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MascotaDTOTest {

    @Test
    @DisplayName("Debería instanciar MascotaRequestDTO y acceder a sus campos")
    void testMascotaRequestDTO() {
        UUID ownerId = UUID.randomUUID();
        MascotaRequestDTO dto = new MascotaRequestDTO("Rex", "Perro", "Pastor", "Grande", PetStatus.LOST, "Parque", ownerId);

        assertEquals("Rex", dto.name());
        assertEquals("Perro", dto.species());
        assertEquals("Pastor", dto.breed());
        assertEquals("Grande", dto.description());
        assertEquals(PetStatus.LOST, dto.status());
        assertEquals("Parque", dto.lastKnownLocation());
        assertEquals(ownerId, dto.ownerId());
    }

    @Test
    @DisplayName("Debería instanciar MascotaResponseDTO y acceder a sus campos")
    void testMascotaResponseDTO() {
        UUID id = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        MascotaResponseDTO dto = new MascotaResponseDTO(id, "Michi", "Gato", "Persa", "Gordo", PetStatus.FOUND, "Techo", ownerId, now);

        assertEquals(id, dto.id());
        assertEquals("Michi", dto.name());
        assertEquals("Gato", dto.species());
        assertEquals("Persa", dto.breed());
        assertEquals("Gordo", dto.description());
        assertEquals(PetStatus.FOUND, dto.status());
        assertEquals("Techo", dto.lastKnownLocation());
        assertEquals(ownerId, dto.ownerId());
        assertEquals(now, dto.createdAt());
    }

    @Test
    @DisplayName("Debería instanciar MascotaUpdateDTO y acceder a sus campos")
    void testMascotaUpdateDTO() {
        MascotaUpdateDTO dto = new MascotaUpdateDTO("Firulais", "Pequeño", PetStatus.RECOVERED, "Casa");

        assertEquals("Firulais", dto.name());
        assertEquals("Pequeño", dto.description());
        assertEquals(PetStatus.RECOVERED, dto.status());
        assertEquals("Casa", dto.lastKnownLocation());
    }
}