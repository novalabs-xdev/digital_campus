package com.flexigp.admissions.auth.controller;

import com.flexigp.admissions.auth.dto.LoginRequest;
import com.flexigp.admissions.auth.dto.LoginResponse;
import com.flexigp.admissions.auth.dto.RegisterRequest;
import com.flexigp.admissions.auth.passwordreset.dto.ForgotPasswordRequest;
import com.flexigp.admissions.auth.passwordreset.dto.ResetPasswordRequest;
import com.flexigp.admissions.auth.passwordreset.dto.VerifyOtpRequest;
import com.flexigp.admissions.auth.passwordreset.dto.VerifyOtpResponse;
import com.flexigp.admissions.common.dto.ApiResult;
import com.flexigp.admissions.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication", description = "Endpoints pour l'inscription, la connexion et la récupération de mot de passe")
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    ResponseEntity<ApiResult<UserResponse>> register(@Valid @RequestBody RegisterRequest request);

    @Operation(summary = "User login (JWT via HttpOnly Cookie)")
    @PostMapping("/login")
    ResponseEntity<ApiResult<LoginResponse>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response);

    @Operation(summary = "Demander un OTP de réinitialisation")
    @PostMapping("/forgot-password")
    ResponseEntity<ApiResult<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request);

    @Operation(summary = "Vérifier l'OTP et obtenir un token UUID")
    @PostMapping("/verify-reset-otp")
    ResponseEntity<ApiResult<VerifyOtpResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request);

    @Operation(summary = "Réinitialiser le mot de passe final")
    @PostMapping("/reset-password")
    ResponseEntity<ApiResult<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request);

    @Operation(summary = "Déconnexion de l'utilisateur")
    @PostMapping("/logout")
    ResponseEntity<ApiResult<Void>> logout(
            HttpServletRequest request,
            HttpServletResponse response
    );

    @Operation(summary = "Récupérer l'utilisateur connecté (via Cookie)")
    @GetMapping("/me")
    ResponseEntity<ApiResult<UserResponse>> getCurrentUser();
}