package com.ism.admissions.candidat.controller;

import com.ism.admissions.candidat.dto.CandidatCreateRequest;
import com.ism.admissions.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Candidats", description = "Gestion des candidats (identité des étudiants)")
@RequestMapping("/api/v1/candidats")
public interface CandidatController {

    @Operation(
            summary = "Créer un candidat",
            description = """
                    Permet de créer un nouveau candidat ou de récupérer un candidat existant
                    si un doublon est détecté (email ou numéro de pièce).
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidat créé ou existant retourné",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données de requête invalides",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflit : candidat déjà existant avec des informations incohérentes",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<?> creerCandidat(
            @Valid @RequestBody CandidatCreateRequest request
    );
}
