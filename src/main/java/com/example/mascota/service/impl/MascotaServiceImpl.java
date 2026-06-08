package com.example.mascota.service.impl;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.dto.MascotaUpdateDTO;
import com.example.mascota.entity.MascotaEntity;
import com.example.mascota.mapper.MascotaMapper;
import com.example.mascota.repository.MascotaRepository;
import com.example.mascota.service.MascotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaMapper mascotaMapper;

    @Override
    @Transactional
    public MascotaResponseDTO registrarMascota(MascotaRequestDTO dto) {
        MascotaEntity entity = mascotaMapper.toEntity(dto);
        MascotaEntity saved = mascotaRepository.save(entity);
        return mascotaMapper.toResponseDto(saved);

    }

    @Override
    @Transactional(readOnly = true)
    public List<MascotaResponseDTO> listarPorPropietario(UUID ownerId) {
        return mascotaRepository.findByOwnerId(ownerId).stream()
                .map(mascotaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MascotaResponseDTO> listarTodas() {
        return mascotaRepository.findAll().stream()
                .map(mascotaMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public MascotaResponseDTO update(UUID id, MascotaUpdateDTO dto) {
        //buscar (si no existe, manda excepción)
        MascotaEntity entity = mascotaRepository.findById(id)


                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        //actualiza campos nulos
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.description() != null) entity.setDescription(dto.description());
        if (dto.status() != null) entity.setStatus(dto.status());
        if (dto.lastKnownLocation() != null) entity.setLastKnownLocation(dto.lastKnownLocation());

        return mascotaMapper.toResponseDto(mascotaRepository.save(entity));
    }
}