package com.ism.admissions.user.controller;

import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.security.current.CurrentUserProvider;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.dto.ChangePasswordRequest;
import com.ism.admissions.user.dto.UpdateUserRequest;
import com.ism.admissions.user.dto.UserProfileResponse;
import com.ism.admissions.user.mapper.UserMapper;
import com.ism.admissions.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public ResponseEntity<ApiResult<UserProfileResponse>> updateMyProfile(UpdateUserRequest request) {
        User user = currentUserProvider.getCurrentUser();
        User updatedUser = userService.updateProfile(user, request);

        // Utilisation de toProfileResponse car c'est une mise à jour de profil complète
        UserProfileResponse response = UserMapper.toProfileResponse(updatedUser);

        return ResponseEntity.ok(ApiResult.success(response, "Profil mis à jour avec succès"));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> changePassword(ChangePasswordRequest request) {
        User user = currentUserProvider.getCurrentUser();
        userService.changePassword(user, request);
        return ResponseEntity.ok(ApiResult.success(null, "Mot de passe modifié"));
    }

    @Override
    public ResponseEntity<ApiResult<Map<String, String>>> uploadAvatar(MultipartFile file) {
        User user = currentUserProvider.getCurrentUser();
        String url = userService.updateAvatar(user, file);

        // Retourne un objet typé Map<String, String> pour le JSON { "url": "..." }
        return ResponseEntity.ok(ApiResult.success(Map.of("url", url), "Avatar mis à jour"));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> deleteAccount() {
        User user = currentUserProvider.getCurrentUser();
        userService.deleteUser(user);
        return ResponseEntity.ok(ApiResult.success(null, "Compte supprimé"));
    }


}