package com.example.mascota.entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BaseEntityTest {

    // Clase concreta para probar la abstracta
    static class DummyEntity extends BaseEntity {
    }

    @Test
    @DisplayName("Debería cubrir al 100% BaseEntity sin problemas de Lombok")
    void testBaseEntityCompleto() {
        DummyEntity base = new DummyEntity();

        // Como la variable ahora se llama "active", Lombok genera isActive() correctamente sin duplicados
        assertTrue(base.isActive());

        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();

        base.setId(id);
        base.setCreatedAt(time);
        base.setUpdatedAt(time);
        base.setActive(false);

        assertEquals(id, base.getId());
        assertEquals(time, base.getCreatedAt());
        assertEquals(time, base.getUpdatedAt());
        assertFalse(base.isActive());
    }
}