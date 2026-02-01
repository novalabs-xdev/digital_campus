package com.ism.admissions.admission.controller;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.dto.AdmissionResponse;
import com.ism.admissions.admission.dto.StatistiquesClasseResponse;
import com.ism.admissions.admission.mapper.AdmissionMapper;
import com.ism.admissions.admission.service.AdmissionService;
import com.ism.admissions.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdmissionControllerImpl implements AdmissionController {
    private final AdmissionService admissionService;
    private final AdmissionMapper admissionMapper;

    @Override
    public ResponseEntity<ApiResult<Void>> creerAdmission(Long candidatureId) {

        admissionService.creerAdmission(candidatureId);

        return ResponseEntity.ok(
                ApiResult.success("Admission créée avec succès")
        );
    }

    @Override
    public ResponseEntity<ApiResult<List<AdmissionResponse>>> listerAdmissions(Long ecoleId, Long classeId) {
        List<Admission> admissions = admissionService.listerAdmissions(ecoleId, classeId);
        return ResponseEntity.ok(ApiResult.success(
                admissionMapper.toResponseList(admissions),
                "Admissions récupérées avec succès"
        ));
    }

    @Override
    public ResponseEntity<ApiResult<StatistiquesClasseResponse>> getStatistiquesClasse(Long classeId) {
        StatistiquesClasseResponse stats = admissionService.getStatistiquesClasse(classeId);
        return ResponseEntity.ok(ApiResult.success(
                stats,
                "Statistiques de la classe récupérées avec succès"
        ));
    }
}
