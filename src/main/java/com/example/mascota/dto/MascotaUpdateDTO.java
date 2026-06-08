package com.example.mascota.dto;

import com.example.mascota.enums.PetStatus;
import jakarta.validation.constraints.Size;


/**
 * DTO para la actualización parcial de una mascota.
 * Solo contiene los campos que el negocio permite modificar.
 */
public record MascotaUpdateDTO(
        @Size(max = 255, message = "El nombre no puede exceder los 255 caracteres")
        String name,

        String description,

        PetStatus status,

        String lastKnownLocation
) {}