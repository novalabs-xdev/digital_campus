package com.ism.admissions.candidature.controller;

import com.ism.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Documents de candidature")
@RequestMapping("/api/v1/candidatures/{candidatureId}/documents")
public interface DocumentCandidatureController {

    @Operation(summary = "Uploader un document pour une candidature")
    @PostMapping(consumes = "multipart/form-data")
    ApiResult<?> uploadDocument(
            @PathVariable @NotNull Long candidatureId,
            @RequestPart("file") MultipartFile file,
            @RequestParam @NotBlank String typeDocument
    );

    @Operation(summary = "Supprimer un document d'une candidature")
    @DeleteMapping("/{documentId}")
    ApiResult<Void> supprimerDocument(
            @PathVariable Long candidatureId,
            @PathVariable Long documentId
    );
}
