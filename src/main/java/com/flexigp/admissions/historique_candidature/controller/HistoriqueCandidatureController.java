package com.flexigp.admissions.historique_candidature.controller;

import com.flexigp.admissions.common.dto.ApiResult;
import com.flexigp.admissions.historique_candidature.dto.HistoriqueStatutResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Candidatures - Historique")
@RequestMapping("/api/v1/candidatures")
public interface HistoriqueCandidatureController {

    @Operation(summary = "Consulter l’historique des statuts d’une candidature")
    @GetMapping("/{candidatureId}/historique")
    ResponseEntity<ApiResult<List<HistoriqueStatutResponse>>> consulterHistorique(
            @PathVariable Long candidatureId
    );
}
