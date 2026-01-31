package com.ism.admissions.candidature.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Candidatures - Agent")
@RequestMapping("/api/v1/candidatures")
public interface CandidatureAgentController {

    @Operation(summary = "Pré-valider une candidature (agent d’admission)")
    @PostMapping("/{candidatureId}/pre-valider")
    ResponseEntity<?> preValiderParAgent(
            @PathVariable Long candidatureId
    );
}