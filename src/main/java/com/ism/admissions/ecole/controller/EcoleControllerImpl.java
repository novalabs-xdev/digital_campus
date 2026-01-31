package com.ism.admissions.ecole.controller;

import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.ecole.dto.EcoleCreateRequest;
import com.ism.admissions.ecole.dto.EcoleResponse;
import com.ism.admissions.ecole.dto.EcoleUpdateRequest;
import com.ism.admissions.ecole.mapper.EcoleMapper;
import com.ism.admissions.ecole.service.EcoleService;
import com.ism.admissions.exception.business.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EcoleControllerImpl implements EcoleController {
    private final EcoleService ecoleService;
    private final EcoleMapper ecoleMapper;

    @Override
    public ResponseEntity<ApiResult<EcoleResponse>> creerEcole(EcoleCreateRequest request) {
        Ecole ecole = ecoleService.creerEcole(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResult.success(
                        ecoleMapper.toResponse(ecole),
                        "École créée avec succès"
                ));
    }

    @Override
    public ResponseEntity<ApiResult<List<EcoleResponse>>> listerEcoles() {
        List<Ecole> ecoles = ecoleService.listerEcoles();
        return ResponseEntity.ok(ApiResult.success(
                ecoleMapper.toResponseList(ecoles),
                "Écoles récupérées avec succès"
        ));
    }

    @Override
    public ResponseEntity<ApiResult<EcoleResponse>> getEcole(Long id) {
        Ecole ecole = ecoleService.getEcole(id)
                .orElseThrow(() -> new ResourceNotFoundException("École introuvable"));

        return ResponseEntity.ok(ApiResult.success(
                ecoleMapper.toResponse(ecole),
                "École récupérée avec succès"
        ));
    }

    @Override
    public ResponseEntity<ApiResult<EcoleResponse>> modifierEcole(
            Long id,
            EcoleUpdateRequest request
    ) {
        Ecole ecole = ecoleService.modifierEcole(id, request);
        return ResponseEntity.ok(ApiResult.success(
                ecoleMapper.toResponse(ecole),
                "École modifiée avec succès"
        ));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> desactiverEcole(Long id) {
        ecoleService.desactiverEcole(id);
        return ResponseEntity.ok(ApiResult.success(
                "École désactivée avec succès"
        ));
    }
}
