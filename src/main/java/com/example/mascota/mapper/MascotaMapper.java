package com.example.mascota.mapper;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.entity.MascotaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    MascotaEntity toEntity(MascotaRequestDTO dto);

    MascotaResponseDTO toResponseDto(MascotaEntity entity);

}