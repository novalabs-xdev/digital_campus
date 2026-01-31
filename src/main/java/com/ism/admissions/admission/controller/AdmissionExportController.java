package com.ism.admissions.admission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Admission Export", description = "Endpoints pour l'exportation des listes d'admis")
@RequestMapping("/api/v1/admissions/export")
public interface AdmissionExportController {

    @Operation(summary = "Exporter les admis d'une classe en Excel")
    @GetMapping("/classe/{classeId}")
    ResponseEntity<Resource> downloadExcelClasse(@PathVariable Long classeId);

    @Operation(summary = "Exporter tous les admis (Arborescence Ecole/Classe en ZIP)")
    @GetMapping("/all")
    ResponseEntity<byte[]> downloadAllExports();
}