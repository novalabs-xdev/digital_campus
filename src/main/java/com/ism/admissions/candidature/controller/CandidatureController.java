package com.ism.admissions.candidature.controller;

import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.candidature.dto.CandidatureDetailResponse;
import com.ism.admissions.candidature.dto.CandidatureResponse;
import com.ism.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Candidatures")
@RequestMapping("/api/v1/candidatures")
public interface CandidatureController {
    @Operation(summary = "Soumettre une candidature")
    @PostMapping("/{candidatureId}/soumettre")
    ResponseEntity<ApiResult<Void>> soumettreCandidature(
            @PathVariable Long candidatureId
    );

    @GetMapping
    ResponseEntity<ApiResult<List<CandidatureResponse>>> listerCandidatures(
            @RequestParam(required = false) StatutCandidature statut,
            @RequestParam(required = false) Long ecoleId,
            @RequestParam(required = false) Long classeId
    );

    @GetMapping("/{id}")
    ResponseEntity<ApiResult<CandidatureDetailResponse>> getCandidature(@PathVariable Long id);
}
