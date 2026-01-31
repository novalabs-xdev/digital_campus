package com.flexigp.admissions.candidature.controller;

import com.flexigp.admissions.candidature.service.CandidatureService;
import com.flexigp.admissions.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CandidatureAgentControllerImpl implements CandidatureAgentController {

    private final CandidatureService candidatureService;

    @Override
    public ResponseEntity<ApiResult<Void>> preValiderParAgent(Long candidatureId) {
        candidatureService.preValiderParAgent(candidatureId);
        return ResponseEntity.ok(
                ApiResult.success("Candidature pré-validée avec succès")
        );
    }
}
