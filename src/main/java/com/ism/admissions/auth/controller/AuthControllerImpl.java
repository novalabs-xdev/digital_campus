package com.ism.admissions.auth.controller;

import com.ism.admissions.auth.domain.AuthResult;
import com.ism.admissions.auth.dto.LoginRequest;
import com.ism.admissions.auth.dto.LoginResponse;
import com.ism.admissions.auth.dto.RegisterRequest;
import com.ism.admissions.auth.passwordreset.dto.ForgotPasswordRequest;
import com.ism.admissions.auth.passwordreset.dto.ResetPasswordRequest;
import com.ism.admissions.auth.passwordreset.dto.VerifyOtpRequest;
import com.ism.admissions.auth.passwordreset.dto.VerifyOtpResponse;
import com.ism.admissions.auth.passwordreset.service.PasswordResetService;
import com.ism.admissions.auth.service.AuthService;
import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.security.current.CurrentUserProvider;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public ResponseEntity<ApiResult<UserResponse>> register(RegisterRequest request) {
        User user = authService.register(request);

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getEmail(),
                null,
                user.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success(userResponse, "Utilisateur inscrit avec succès"));
    }

    @Override
    public ResponseEntity<ApiResult<LoginResponse>> login(LoginRequest request, HttpServletResponse response) {
        AuthResult result = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", result.token())
                .httpOnly(true)
                .secure(false) // Mettre à true en production (HTTPS)
                .sameSite("Strict")
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        LoginResponse loginResponse = new LoginResponse(
                null,
                result.user().getEmail(),
                result.user().getRole()
        );

        return ResponseEntity.ok(ApiResult.success(loginResponse, "Connexion réussie"));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> forgotPassword(ForgotPasswordRequest request) {
        passwordResetService.forgotPassword(request);
        return ResponseEntity.ok(ApiResult.success(
                "Si votre email est associé à un compte, vous recevrez un code de vérification."
        ));
    }

    @Override
    public ResponseEntity<ApiResult<VerifyOtpResponse>> verifyOtp(VerifyOtpRequest request) {
        VerifyOtpResponse data = passwordResetService.verifyOtp(request);
        return ResponseEntity.ok(ApiResult.success(data, data.message()));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> resetPassword(ResetPasswordRequest request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.ok(ApiResult.success("Votre mot de passe a été réinitialisé avec succès."));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.logout(request, response);
        return ResponseEntity.noContent().build();
    }


    @Override
    @PreAuthorize("isAuthenticated()") // Sécurité : seul un utilisateur avec cookie valide accède
    public ResponseEntity<ApiResult<UserResponse>> getCurrentUser() {
        User user = currentUserProvider.getCurrentUser();

        // On mappe vers UserResponse (ou LoginResponse selon ton besoin front)
        UserResponse response = new UserResponse(
                user.getId(),
                user.getEmail(),
                null,
                user.getRole()
        );

        return ResponseEntity.ok(ApiResult.success(response, "Utilisateur récupéré avec succès"));
    }
}