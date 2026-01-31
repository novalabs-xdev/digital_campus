package com.flexigp.admissions.admission.controller;

import com.flexigp.admissions.admission.service.AdmissionService;
import com.flexigp.admissions.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdmissionControllerImpl implements AdmissionController {
    private final AdmissionService admissionService;

    @Override
    public ResponseEntity<ApiResult<Void>> creerAdmission(Long candidatureId) {

        admissionService.creerAdmission(candidatureId);

        return ResponseEntity.ok(
                ApiResult.success("Admission créée avec succès")
        );
    }
}
