package com.ism.admissions.candidature.controller;

import com.ism.admissions.candidature.service.CandidatureService;
import com.ism.admissions.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CandidatureControllerImpl implements CandidatureController {

    private final CandidatureService candidatureService;

    @Override
    public ResponseEntity<ApiResult<Void>> soumettreCandidature(Long candidatureId) {
        candidatureService.soumettreCandidature(candidatureId);
        return ResponseEntity.ok(
                ApiResult.success("Candidature soumise avec succès")
        );
    }
}
