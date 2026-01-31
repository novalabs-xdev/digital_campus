package com.ism.admissions.admission.controller;

import com.ism.admissions.admission.dto.AdmissionResponse;
import com.ism.admissions.admission.dto.StatistiquesClasseResponse;
import com.ism.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admissions")
@RequestMapping("/api/v1/admissions")
public interface AdmissionController {

    @Operation(summary = "Créer l’admission à partir d’une candidature validée")
    @PostMapping("/candidatures/{candidatureId}")
    ResponseEntity<ApiResult<Void>> creerAdmission(
            @PathVariable Long candidatureId
    );

    @GetMapping
    ResponseEntity<ApiResult<List<AdmissionResponse>>> listerAdmissions(
            @RequestParam(required = false) Long ecoleId,
            @RequestParam(required = false) Long classeId
    );

    @GetMapping("/classe/{classeId}/stats")
    ResponseEntity<ApiResult<StatistiquesClasseResponse>> getStatistiquesClasse(@PathVariable Long classeId);
}
