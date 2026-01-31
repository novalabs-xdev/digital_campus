package com.ism.admissions.candidature.controller;

import com.ism.admissions.candidature.service.DocumentCandidatureService;
import com.ism.admissions.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DocumentCandidatureControllerImpl implements DocumentCandidatureController {

    private final DocumentCandidatureService documentCandidatureService;

    @Override
    public ApiResult<Void> uploadDocument(
            Long candidatureId,
            MultipartFile file,
            String typeDocument
    ) {
        documentCandidatureService.ajouterDocument(
                candidatureId,
                file,
                typeDocument
        );
        return ApiResult.success("Document ajouté avec succès");
    }

    @Override
    public ApiResult<Void> supprimerDocument(
            Long candidatureId,
            Long documentId
    ) {
        documentCandidatureService.supprimerDocument(
                candidatureId,
                documentId
        );
        return ApiResult.success("Document supprimé avec succès");
    }
}
