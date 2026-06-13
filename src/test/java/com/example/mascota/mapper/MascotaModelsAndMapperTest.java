package com.example.mascota.mapper;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.dto.MascotaUpdateDTO;
import com.example.mascota.entity.MascotaEntity;
import com.example.mascota.enums.PetStatus;
import com.example.mascota.mapper.MascotaMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MascotaModelsAndMapperTest {

    @Test
    @DisplayName("Debería cubrir los métodos del Enum PetStatus")
    void testEnums() {
        PetStatus status = PetStatus.valueOf("LOST");
        assertEquals(PetStatus.LOST, status);

        PetStatus[] values = PetStatus.values();
        assertTrue(values.length > 0);
    }

    @Test
    @DisplayName("Debería cubrir constructores, getters y setters de BaseEntity y MascotaEntity")
    void testEntity() {
        MascotaEntity entity = new MascotaEntity();
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        entity.setId(id);
        entity.setName("Firulais");
        entity.setSpecies("Perro");
        entity.setBreed("Kiltro");
        entity.setDescription("Guau");
        entity.setStatus(PetStatus.FOUND);
        entity.setLastKnownLocation("Plaza");
        entity.setOwnerId(id);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(true);

        assertEquals(id, entity.getId());
        assertEquals("Firulais", entity.getName());
        assertEquals("Perro", entity.getSpecies());
        assertEquals("Kiltro", entity.getBreed());
        assertEquals("Guau", entity.getDescription());
        assertEquals(PetStatus.FOUND, entity.getStatus());
        assertEquals("Plaza", entity.getLastKnownLocation());
        assertEquals(id, entity.getOwnerId());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertTrue(entity.isActive());

        MascotaEntity entityAll = new MascotaEntity("Rex", "Perro", "Pastor", "Grande", PetStatus.LOST, "Parque", id);
        assertEquals("Rex", entityAll.getName());
    }

    @Test
    @DisplayName("Debería cubrir la instanciación de los Records (DTOs)")
    void testDTOs() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        MascotaRequestDTO req = new MascotaRequestDTO("A", "B", "C", "D", PetStatus.LOST, "E", id);
        assertEquals("A", req.name());

        MascotaResponseDTO res = new MascotaResponseDTO(id, "A", "B", "C", "D", PetStatus.LOST, "E", id, now);
        assertEquals("A", res.name());

        MascotaUpdateDTO upd = new MascotaUpdateDTO("A", "D", PetStatus.LOST, "E");
        assertEquals("A", upd.name());
    }

    @Test
    @DisplayName("Debería cubrir el Mapper generado por MapStruct")
    void testMapper() {
        MascotaMapperImpl mapper = new MascotaMapperImpl();

        MascotaRequestDTO req = new MascotaRequestDTO("Rex", "Perro", "Pastor", "Grande", PetStatus.LOST, "Parque", UUID.randomUUID());
        MascotaEntity entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals("Rex", entity.getName());
        assertNull(entity.getId());

        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(LocalDateTime.now());
        MascotaResponseDTO res = mapper.toResponseDto(entity);

        assertNotNull(res);
        assertEquals("Rex", res.name());
        assertNotNull(res.id());

        assertNull(mapper.toEntity(null));
        assertNull(mapper.toResponseDto(null));
    }
}
