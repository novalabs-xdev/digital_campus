package com.ism.admissions.candidature.controller;

import com.ism.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Candidatures - Directeur")
@RequestMapping("/api/v1/candidatures")
public interface CandidatureDirecteurController {

    @Operation(summary = "Valider une candidature (directeur)")
    @PostMapping("/{candidatureId}/valider")
    ResponseEntity<ApiResult<Void>> validerParDirecteur(
            @PathVariable Long candidatureId
    );

    @Operation(summary = "Rejeter une candidature (directeur)")
    @PostMapping("/{candidatureId}/rejeter")
    ResponseEntity<ApiResult<Void>> rejeterParDirecteur(
            @PathVariable Long candidatureId,
            @RequestParam String motif
    );
}