package com.ism.admissions.historique_candidature.controller;

import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.historique_candidature.dto.HistoriqueStatutResponse;
import com.ism.admissions.historique_candidature.mapper.HistoriqueCandidatureMapper;
import com.ism.admissions.historique_candidature.service.HistoriqueCandidatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoriqueCandidatureControllerImpl implements HistoriqueCandidatureController {
    private final HistoriqueCandidatureService historiqueService;
    private final HistoriqueCandidatureMapper mapper;

    @Override
    public ResponseEntity<ApiResult<List<HistoriqueStatutResponse>>> consulterHistorique(
            Long candidatureId
    ) {
        var historiques = historiqueService.consulterHistorique(candidatureId);

        return ResponseEntity.ok(
                ApiResult.success(
                        mapper.toResponseList(historiques),
                        "Historique récupéré avec succès"
                )
        );
    }
}
