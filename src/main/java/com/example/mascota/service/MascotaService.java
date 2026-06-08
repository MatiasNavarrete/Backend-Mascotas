package com.example.mascota.service;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.dto.MascotaUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface MascotaService {

    MascotaResponseDTO registrarMascota(MascotaRequestDTO dto);

    List<MascotaResponseDTO> listarPorPropietario(UUID ownerId);

    List<MascotaResponseDTO> listarTodas();

    MascotaResponseDTO update(UUID id, MascotaUpdateDTO dto);
}