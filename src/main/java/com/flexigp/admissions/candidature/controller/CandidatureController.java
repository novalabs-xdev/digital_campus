package com.flexigp.admissions.candidature.controller;

import com.flexigp.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Candidatures")
@RequestMapping("/api/v1/candidatures")
public interface CandidatureController {
    @Operation(summary = "Soumettre une candidature")
    @PostMapping("/{candidatureId}/soumettre")
    ResponseEntity<ApiResult<Void>> soumettreCandidature(
            @PathVariable Long candidatureId
    );
}
