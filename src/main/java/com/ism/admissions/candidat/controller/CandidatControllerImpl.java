package com.ism.admissions.candidat.controller;


import com.ism.admissions.candidat.domain.Candidat;
import com.ism.admissions.candidat.dto.CandidatCreateRequest;
import com.ism.admissions.candidat.dto.CandidatResponse;
import com.ism.admissions.candidat.mapper.CandidatMapper;
import com.ism.admissions.candidat.service.CandidatService;
import com.ism.admissions.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CandidatControllerImpl implements CandidatController {

    private final CandidatService candidatService;
    private final CandidatMapper candidatMapper;


    @Override
    public ResponseEntity<ApiResult<CandidatResponse>> creerCandidat(
            CandidatCreateRequest request
    ) {
        Candidat candidat = candidatMapper.toEntity(request);
        Candidat saved = candidatService.creerCandidat(candidat);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResult.success(
                        candidatMapper.toResponse(saved),
                        "Candidat créé avec succès"
                ));
    }
}
