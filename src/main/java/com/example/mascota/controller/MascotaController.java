package com.example.mascota.controller;

import com.example.mascota.dto.MascotaRequestDTO;
import com.example.mascota.dto.MascotaResponseDTO;
import com.example.mascota.dto.MascotaUpdateDTO;
import com.example.mascota.entity.MascotaEntity;
import com.example.mascota.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> registrar(@Valid @RequestBody MascotaRequestDTO dto) {
        return new ResponseEntity<>(mascotaService.registrarMascota(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> listarTodas() {
        return  ResponseEntity.ok(mascotaService.listarTodas());

    }

    @GetMapping("/propietario/{ownerId}")
    public ResponseEntity<List<MascotaResponseDTO>> listarPorPropietario(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(mascotaService.listarPorPropietario(ownerId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody MascotaUpdateDTO dto) {
        return ResponseEntity.ok(mascotaService.update(id,dto));
    }
}