package com.ism.admissions.candidature.controller;

import com.ism.admissions.candidature.service.CandidatureService;
import com.ism.admissions.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CandidatureDirecteurControllerImpl implements CandidatureDirecteurController {

    private final CandidatureService candidatureService;

    @Override
    public ResponseEntity<ApiResult<Void>> validerParDirecteur(Long candidatureId) {

        candidatureService.validerParDirecteur(candidatureId);

        return ResponseEntity.ok(
                ApiResult.success("Candidature validée avec succès")
        );
    }

    @Override
    public ResponseEntity<ApiResult<Void>> rejeterParDirecteur(
            Long candidatureId,
            String motif
    ) {
        candidatureService.rejeterParDirecteur(candidatureId, motif);

        return ResponseEntity.ok(
                ApiResult.success("Candidature rejetée avec succès")
        );
    }
}
