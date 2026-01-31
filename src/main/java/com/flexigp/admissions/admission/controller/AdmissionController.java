package com.flexigp.admissions.admission.controller;

import com.flexigp.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Admissions")
@RequestMapping("/api/v1/admissions")
public interface AdmissionController {

    @Operation(summary = "Créer l’admission à partir d’une candidature validée")
    @PostMapping("/candidatures/{candidatureId}")
    ResponseEntity<ApiResult<Void>> creerAdmission(
            @PathVariable Long candidatureId
    );
}
