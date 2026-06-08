package com.example.mascota.dto;

import com.example.mascota.enums.PetStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record MascotaResponseDTO(

        UUID id,
        String name,
        String species,
        String breed,
        String description,
        PetStatus status,
        String lastKnownLocation,
        UUID ownerId,
        LocalDateTime createdAt

) {}