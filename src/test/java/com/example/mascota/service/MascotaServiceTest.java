package com.example.mascota.service;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.dto.MascotaUpdateDTO;
import com.example.mascota.entity.MascotaEntity;
import com.example.mascota.enums.PetStatus;
import com.example.mascota.mapper.MascotaMapper;
import com.example.mascota.repository.MascotaRepository;
import com.example.mascota.service.impl.MascotaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private MascotaMapper mascotaMapper;

    @InjectMocks
    private MascotaServiceImpl mascotaService;

    @Test
    @DisplayName("Debería registrar una mascota exitosamente")
    void registrarMascota_Exito() {
        UUID ownerId = UUID.randomUUID();
        MascotaRequestDTO request = new MascotaRequestDTO("Firulais", "Perro", "Kiltro", "Perrito chiquito", PetStatus.LOST, "Plaza", ownerId);
        MascotaEntity entity = new MascotaEntity();
        entity.setName("Firulais");

        MascotaResponseDTO response = new MascotaResponseDTO(UUID.randomUUID(), "Firulais", "Perro", "Kiltro", "Perrito chiquito", PetStatus.LOST, "Plaza", ownerId, LocalDateTime.now());

        when(mascotaMapper.toEntity(any(MascotaRequestDTO.class))).thenReturn(entity);
        when(mascotaRepository.save(any(MascotaEntity.class))).thenReturn(entity);
        when(mascotaMapper.toResponseDto(any(MascotaEntity.class))).thenReturn(response);

        MascotaResponseDTO resultado = mascotaService.registrarMascota(request);

        assertNotNull(resultado);
        assertEquals("Firulais", resultado.name());
        verify(mascotaRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Debería listar todas las mascotas de un propietario")
    void listarPorPropietario_Exito() {
        UUID ownerId = UUID.randomUUID();
        MascotaEntity entity = new MascotaEntity();
        MascotaResponseDTO response = new MascotaResponseDTO(UUID.randomUUID(), "Michi", "Gato", "Persa", "Gato flojo", PetStatus.FOUND, "Casa", ownerId, LocalDateTime.now());

        when(mascotaRepository.findByOwnerId(ownerId)).thenReturn(List.of(entity));
        when(mascotaMapper.toResponseDto(any(MascotaEntity.class))).thenReturn(response);

        List<MascotaResponseDTO> resultado = mascotaService.listarPorPropietario(ownerId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Michi", resultado.get(0).name());
        verify(mascotaRepository, times(1)).findByOwnerId(ownerId);
    }

    @Test
    @DisplayName("Debería listar absolutamente todas las mascotas")
    void listarTodas_Exito() {
        MascotaEntity entity = new MascotaEntity();
        MascotaResponseDTO response = new MascotaResponseDTO(UUID.randomUUID(), "Cachupin", "Perro", "Pug", "Respira raro", PetStatus.SIGHTED, "Calle 1", UUID.randomUUID(), LocalDateTime.now());

        when(mascotaRepository.findAll()).thenReturn(List.of(entity));
        when(mascotaMapper.toResponseDto(any(MascotaEntity.class))).thenReturn(response);

        List<MascotaResponseDTO> resultado = mascotaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cachupin", resultado.get(0).name());
    }

    @Test
    @DisplayName("Debería actualizar los campos nulos y guardar la mascota correctamente")
    void update_Exito() {
        UUID mascotaId = UUID.randomUUID();
        MascotaUpdateDTO updateDto = new MascotaUpdateDTO("Firulais Actualizado", "Nueva descripcion", PetStatus.RECOVERED, "En mi casa");

        MascotaEntity entityEnBd = new MascotaEntity();
        entityEnBd.setId(mascotaId);
        entityEnBd.setName("Firulais Viejo");

        MascotaResponseDTO response = new MascotaResponseDTO(mascotaId, "Firulais Actualizado", "Perro", "Kiltro", "Nueva descripcion", PetStatus.RECOVERED, "En mi casa", UUID.randomUUID(), LocalDateTime.now());

        when(mascotaRepository.findById(mascotaId)).thenReturn(Optional.of(entityEnBd));
        when(mascotaRepository.save(any(MascotaEntity.class))).thenReturn(entityEnBd);
        when(mascotaMapper.toResponseDto(any(MascotaEntity.class))).thenReturn(response);

        MascotaResponseDTO resultado = mascotaService.update(mascotaId, updateDto);

        assertNotNull(resultado);
        assertEquals("Firulais Actualizado", resultado.name());
        assertEquals(PetStatus.RECOVERED, resultado.status());

        assertEquals("Firulais Actualizado", entityEnBd.getName());
        assertEquals(PetStatus.RECOVERED, entityEnBd.getStatus());
        verify(mascotaRepository, times(1)).save(entityEnBd);
    }

    @Test
    @DisplayName("Debería lanzar excepción si se intenta actualizar una mascota que no existe")
    void update_NoEncontrado() {
        UUID mascotaId = UUID.randomUUID();
        MascotaUpdateDTO updateDto = new MascotaUpdateDTO("Fantasma", null, null, null);

        when(mascotaRepository.findById(mascotaId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mascotaService.update(mascotaId, updateDto));
        assertEquals("Mascota no encontrada", exception.getMessage());

        verify(mascotaRepository, never()).save(any());
    }
    @Test
    @DisplayName("Debería actualizar ignorando los campos nulos (Cubre las 4 ramas faltantes)")
    void update_ConCamposNulos_DeberiaMantenerValoresOriginales() {
        UUID mascotaId = UUID.randomUUID();
        MascotaUpdateDTO updateDtoNulo = new MascotaUpdateDTO(null, null, null, null);

        MascotaEntity entityEnBd = new MascotaEntity();
        entityEnBd.setId(mascotaId);
        entityEnBd.setName("Nombre Original");
        entityEnBd.setStatus(PetStatus.LOST);

        MascotaResponseDTO responseMock = new MascotaResponseDTO(mascotaId, "Nombre Original", "Perro", "Raza", "Desc", PetStatus.LOST, "Lugar", UUID.randomUUID(), LocalDateTime.now());

        when(mascotaRepository.findById(mascotaId)).thenReturn(Optional.of(entityEnBd));
        when(mascotaRepository.save(any(MascotaEntity.class))).thenReturn(entityEnBd);
        when(mascotaMapper.toResponseDto(any(MascotaEntity.class))).thenReturn(responseMock);

        MascotaResponseDTO resultado = mascotaService.update(mascotaId, updateDtoNulo);

        assertNotNull(resultado);
        assertEquals("Nombre Original", entityEnBd.getName());
        assertEquals(PetStatus.LOST, entityEnBd.getStatus());

        verify(mascotaRepository, times(1)).save(entityEnBd);
    }
}