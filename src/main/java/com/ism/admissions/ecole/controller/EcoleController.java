package com.ism.admissions.ecole.controller;

import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.ecole.dto.EcoleCreateRequest;
import com.ism.admissions.ecole.dto.EcoleResponse;
import com.ism.admissions.ecole.dto.EcoleUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Écoles")
@RequestMapping("/api/v1/ecoles")
public interface EcoleController {

    @Operation(summary = "Créer une école")
    @PostMapping
    ResponseEntity<ApiResult<EcoleResponse>> creerEcole(
            @Valid @RequestBody EcoleCreateRequest request
    );

    @Operation(summary = "Lister toutes les écoles")
    @GetMapping
    ResponseEntity<ApiResult<List<EcoleResponse>>> listerEcoles();

    @Operation(summary = "Obtenir une école par ID")
    @GetMapping("/{id}")
    ResponseEntity<ApiResult<EcoleResponse>> getEcole(@PathVariable Long id);

    @Operation(summary = "Modifier une école")
    @PutMapping("/{id}")
    ResponseEntity<ApiResult<EcoleResponse>> modifierEcole(
            @PathVariable Long id,
            @Valid @RequestBody EcoleUpdateRequest request
    );

    @Operation(summary = "Désactiver une école")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResult<Void>> desactiverEcole(@PathVariable Long id);
}