package com.example.mascota.entity;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.dto.MascotaUpdateDTO;
import com.example.mascota.entity.MascotaEntity;
import com.example.mascota.enums.PetStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MascotaModelsTest {

    @Test
    @DisplayName("Debería cubrir constructores, getters y setters de MascotaEntity y BaseEntity")
    void testMascotaEntity() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        MascotaEntity entity = new MascotaEntity();
        entity.setId(id);
        entity.setName("Rex");
        entity.setSpecies("Perro");
        entity.setBreed("Pastor");
        entity.setDescription("Grande");
        entity.setStatus(PetStatus.FOUND);
        entity.setLastKnownLocation("Parque");
        entity.setOwnerId(id);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(true);

        assertEquals(id, entity.getId());
        assertEquals("Rex", entity.getName());
        assertEquals("Perro", entity.getSpecies());
        assertEquals("Pastor", entity.getBreed());
        assertEquals("Grande", entity.getDescription());
        assertEquals(PetStatus.FOUND, entity.getStatus());
        assertEquals("Parque", entity.getLastKnownLocation());
        assertEquals(id, entity.getOwnerId());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertTrue(entity.isActive());

        MascotaEntity entityAllArgs = new MascotaEntity("Michi", "Gato", "Persa", "Chico", PetStatus.LOST, "Techo", id);
        assertEquals("Michi", entityAllArgs.getName());
    }

    @Test
    @DisplayName("Debería instanciar correctamente los Records (DTOs)")
    void testDTOs() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        MascotaRequestDTO request = new MascotaRequestDTO("Rex", "Perro", "Pastor", "Grande", PetStatus.LOST, "Parque", id);
        assertEquals("Rex", request.name());

        MascotaResponseDTO response = new MascotaResponseDTO(id, "Rex", "Perro", "Pastor", "Grande", PetStatus.LOST, "Parque", id, now);
        assertEquals(id, response.id());

        MascotaUpdateDTO update = new MascotaUpdateDTO("Rex 2", "Chico", PetStatus.RECOVERED, "Casa");
        assertEquals("Rex 2", update.name());
    }

    @Test
    @DisplayName("Debería cubrir los métodos internos del Enum PetStatus")
    void testPetStatusEnum() {
        PetStatus[] statuses = PetStatus.values();
        assertTrue(statuses.length > 0);

        PetStatus status = PetStatus.valueOf("LOST");
        assertEquals(PetStatus.LOST, status);
    }
    @Test
    @DisplayName("Debería cubrir BaseEntity al 100% incluyendo su constructor y booleanos")
    void testBaseEntityDirectamente() {
        BaseEntity base = new BaseEntity() {};

        assertTrue(base.isActive());

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        base.setId(id);
        base.setCreatedAt(now);
        base.setUpdatedAt(now);
        base.setActive(false);

        assertEquals(id, base.getId());
        assertEquals(now, base.getCreatedAt());
        assertEquals(now, base.getUpdatedAt());
        assertFalse(base.isActive());
    }
}