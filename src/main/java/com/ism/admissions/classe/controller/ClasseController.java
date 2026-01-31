package com.ism.admissions.classe.controller;

import com.ism.admissions.classe.dto.ClasseCreateRequest;
import com.ism.admissions.classe.dto.ClasseResponse;
import com.ism.admissions.classe.dto.ClasseUpdateRequest;
import com.ism.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Classes", description = "Gestion des classes")
@RequestMapping("/api/v1/classes")
public interface ClasseController {

    @Operation(summary = "Créer une classe")
    @PostMapping
    ResponseEntity<ApiResult<ClasseResponse>> creerClasse(
            @Valid @RequestBody ClasseCreateRequest request
    );

    @Operation(summary = "Lister les classes d'une école")
    @GetMapping("/ecole/{ecoleId}")
    ResponseEntity<ApiResult<List<ClasseResponse>>> listerClassesParEcole(
            @PathVariable Long ecoleId
    );

    @Operation(summary = "Obtenir une classe par ID")
    @GetMapping("/{id}")
    ResponseEntity<ApiResult<ClasseResponse>> getClasse(@PathVariable Long id);

    @Operation(summary = "Modifier une classe")
    @PutMapping("/{id}")
    ResponseEntity<ApiResult<ClasseResponse>> modifierClasse(
            @PathVariable Long id,
            @Valid @RequestBody ClasseUpdateRequest request
    );

    @Operation(summary = "Supprimer une classe")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResult<Void>> supprimerClasse(@PathVariable Long id);
}