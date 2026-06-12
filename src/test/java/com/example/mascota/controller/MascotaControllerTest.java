package com.example.mascota.controller;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.dto.MascotaUpdateDTO;
import com.example.mascota.enums.PetStatus;
import com.example.mascota.service.MascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MascotaService mascotaService;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/v1/mascotas - Debería registrar una mascota y retornar 201 Created")
    void registrarMascota_Retorna201() throws Exception {
        UUID ownerId = UUID.randomUUID();
        MascotaRequestDTO request = new MascotaRequestDTO("Rex", "Perro", "Pastor Aleman", "Grande", PetStatus.LOST, "Parque", ownerId);
        MascotaResponseDTO response = new MascotaResponseDTO(UUID.randomUUID(), "Rex", "Perro", "Pastor Aleman", "Grande", PetStatus.LOST, "Parque", ownerId, LocalDateTime.now());

        when(mascotaService.registrarMascota(any(MascotaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Rex"))
                .andExpect(jsonPath("$.species").value("Perro"));
    }

    @Test
    @DisplayName("GET /api/v1/mascotas - Debería listar todas las mascotas y retornar 200 OK")
    void listarTodas_Retorna200() throws Exception {
        MascotaResponseDTO response = new MascotaResponseDTO(UUID.randomUUID(), "Michi", "Gato", "Persa", "Gordo", PetStatus.FOUND, "Techo", UUID.randomUUID(), LocalDateTime.now());

        when(mascotaService.listarTodas()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/mascotas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Michi"));
    }

    @Test
    @DisplayName("GET /api/v1/mascotas/propietario/{ownerId} - Debería listar por dueño y retornar 200 OK")
    void listarPorPropietario_Retorna200() throws Exception {
        UUID ownerId = UUID.randomUUID();
        MascotaResponseDTO response = new MascotaResponseDTO(UUID.randomUUID(), "Cachupin", "Perro", "Kiltro", "Chico", PetStatus.SIGHTED, "Plaza", ownerId, LocalDateTime.now());

        when(mascotaService.listarPorPropietario(ownerId)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/mascotas/propietario/{ownerId}", ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].ownerId").value(ownerId.toString()));
    }

    @Test
    @DisplayName("PATCH /api/v1/mascotas/{id} - Debería actualizar mascota y retornar 200 OK")
    void actualizarMascota_Retorna200() throws Exception {
        UUID mascotaId = UUID.randomUUID();
        MascotaUpdateDTO updateDto = new MascotaUpdateDTO("Rex Actualizado", null, PetStatus.RECOVERED, null);
        MascotaResponseDTO response = new MascotaResponseDTO(mascotaId, "Rex Actualizado", "Perro", "Pastor Aleman", "Grande", PetStatus.RECOVERED, "Parque", UUID.randomUUID(), LocalDateTime.now());

        when(mascotaService.update(eq(mascotaId), any(MascotaUpdateDTO.class))).thenReturn(response);

        mockMvc.perform(patch("/api/v1/mascotas/{id}", mascotaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rex Actualizado"))
                .andExpect(jsonPath("$.status").value("RECOVERED"));
    }
}