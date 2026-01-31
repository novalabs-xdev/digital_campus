package com.flexigp.admissions.user.controller;

import com.flexigp.admissions.user.dto.ChangePasswordRequest;
import com.flexigp.admissions.user.dto.UpdateUserRequest;
import com.flexigp.admissions.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Users", description = "Gestion du compte et des informations personnelles de l'utilisateur")
@RequestMapping("/api/v1/users")
public interface UserController {

//    @Operation(
//            summary = "Récupérer mon profil",
//            description = "Retourne les informations de l'utilisateur actuellement authentifié via son token JWT."
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Profil récupéré avec succès",
//                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
//            @ApiResponse(responseCode = "401", description = "Non authentifié")
//    })
//    @GetMapping("/me")
//    ResponseEntity<?> getMyProfile();

    @Operation(
            summary = "Mettre à jour mon profil",
            description = "Permet de modifier le nom, prénom ou d'autres informations de base."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profil mis à jour",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "422", description = "Erreur de validation des données")
    })
    @PutMapping("/me")
    ResponseEntity<?> updateMyProfile(@RequestBody UpdateUserRequest request);

    @Operation(
            summary = "Changer le mot de passe",
            description = "Action sécurisée nécessitant l'ancien et le nouveau mot de passe."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mot de passe modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "L'ancien mot de passe est incorrect"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PutMapping("/me/password")
    ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request);

    /**
     * Uploader une photo de profil
     */
    @Operation(summary = "Upload avatar", description = "Mettre à jour la photo de profil de l'utilisateur")
    @PostMapping(value = "/me/avatar", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> uploadAvatar(
            @Parameter(description = "Fichier image")
            @RequestPart("file") MultipartFile file
    );

    /**
     * Supprimer/Désactiver son propre compte
     */
    @Operation(summary = "Supprimer mon compte", description = "Désactivation définitive du compte utilisateur")
    @DeleteMapping("/me")
    ResponseEntity<?> deleteAccount();

}