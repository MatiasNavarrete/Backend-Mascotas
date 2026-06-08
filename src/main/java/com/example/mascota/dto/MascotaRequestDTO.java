package com.example.mascota.dto;

import com.example.mascota.enums.PetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record MascotaRequestDTO(
        @NotBlank(message = "El nombre de la mascota es obligatorio")
        String name,

        @NotBlank(message = "La especie de la mascota es obligatoria")
        String species,

        String breed,

        @Size(max = 600, message = "La descripción de la mascota no puede superar los 600 caracteres")
        String description,

        @NotNull(message = "El estado es obligatorio")
        PetStatus status,

        String lastKnownLocation,

        @NotNull(message = "El ID del propietario es obligatorio")
        UUID ownerId
) {}