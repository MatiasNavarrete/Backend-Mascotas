package com.example.mascota.repository;

import com.example.mascota.entity.MascotaEntity;
import com.example.mascota.enums.PetStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MascotaRepositoryTest {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Test
    void findByOwnerId_DeberiaRetornarMascotasDelDuenio() {
        UUID ownerId = UUID.randomUUID();
        MascotaEntity mascota = new MascotaEntity();
        mascota.setName("Rex");
        mascota.setSpecies("Perro");
        mascota.setStatus(PetStatus.LOST);
        mascota.setOwnerId(ownerId);
        mascotaRepository.save(mascota);

        List<MascotaEntity> resultado = mascotaRepository.findByOwnerId(ownerId);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getName()).isEqualTo("Rex");
    }
}