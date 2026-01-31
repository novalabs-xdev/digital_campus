package com.ism.admissions.classe.controller;

import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.classe.dto.ClasseCreateRequest;
import com.ism.admissions.classe.dto.ClasseResponse;
import com.ism.admissions.classe.dto.ClasseUpdateRequest;
import com.ism.admissions.classe.mapper.ClasseMapper;
import com.ism.admissions.classe.service.ClasseService;
import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.exception.business.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClasseControllerImpl implements ClasseController {

    private final ClasseService classeService;
    private final ClasseMapper classeMapper;

    @Override
    public ResponseEntity<ApiResult<ClasseResponse>> creerClasse(ClasseCreateRequest request) {
        Classe classe = classeService.creerClasse(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResult.success(
                        classeMapper.toResponse(classe),
                        "Classe créée avec succès"
                ));
    }

    @Override
    public ResponseEntity<ApiResult<List<ClasseResponse>>> listerClassesParEcole(Long ecoleId) {
        List<Classe> classes = classeService.listerClassesParEcole(ecoleId);
        return ResponseEntity.ok(ApiResult.success(
                classeMapper.toResponseList(classes),
                "Classes récupérées avec succès"
        ));
    }

    @Override
    public ResponseEntity<ApiResult<ClasseResponse>> getClasse(Long id) {
        Classe classe = classeService.getClasse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe introuvable"));

        return ResponseEntity.ok(ApiResult.success(
                classeMapper.toResponse(classe),
                "Classe récupérée avec succès"
        ));
    }

    @Override
    public ResponseEntity<ApiResult<ClasseResponse>> modifierClasse(
            Long id,
            ClasseUpdateRequest request
    ) {
        Classe classe = classeService.modifierClasse(id, request);
        return ResponseEntity.ok(ApiResult.success(
                classeMapper.toResponse(classe),
                "Classe modifiée avec succès"
        ));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> supprimerClasse(Long id) {
        classeService.supprimerClasse(id);
        return ResponseEntity.ok(ApiResult.success(
                "Classe supprimée avec succès"
        ));
    }
}