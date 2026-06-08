package com.example.mascota.repository;

import com.example.mascota.entity.MascotaEntity;
import com.example.mascota.enums.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MascotaRepository extends JpaRepository<MascotaEntity, UUID> {

    //Buscar todas las mascotas de un dueño según la id del dueño
    List<MascotaEntity> findByOwnerId(UUID ownerId);

    //Buscar por estado (Para ver quiénes están perdidos, avistados, etc.)
    List<MascotaEntity> findByStatus(PetStatus status);

    //Buscar por especie (Para filtrar si buscas un perro o un gato por ejemplo)
    List<MascotaEntity> findBySpeciesIgnoreCase(String species);

    //Buscar por raza
    List<MascotaEntity> findByBreedContainingIgnoreCase(String breed);

    //Combinado: Buscar mascotas perdidas por especie
    List<MascotaEntity> findByStatusAndSpecies(PetStatus status, String species);

}