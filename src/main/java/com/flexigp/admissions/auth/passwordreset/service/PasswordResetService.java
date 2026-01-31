package com.flexigp.admissions.auth.passwordreset.service;

import com.flexigp.admissions.auth.passwordreset.dto.ForgotPasswordRequest;
import com.flexigp.admissions.auth.passwordreset.dto.ResetPasswordRequest;
import com.flexigp.admissions.auth.passwordreset.dto.VerifyOtpRequest;
import com.flexigp.admissions.auth.passwordreset.dto.VerifyOtpResponse;

public interface PasswordResetService {
    /**
     * Génère un OTP de 6 chiffres, l'enregistre et déclenche l'envoi d'un email.
     *
     * @param request Contient l'email de l'utilisateur.
     */
    void forgotPassword(ForgotPasswordRequest request);

    /**
     * Vérifie la validité de l'OTP et génère un token UUID pour la réinitialisation.
     *
     * @param request Contient l'email et l'OTP à 6 chiffres.
     * @return VerifyOtpResponse contenant le token de session.
     */
    VerifyOtpResponse verifyOtp(VerifyOtpRequest request);

    /**
     * Change le mot de passe de l'utilisateur si le token est valide et non expiré.
     *
     * @param request Contient le token et le nouveau mot de passe.
     */
    void resetPassword(ResetPasswordRequest request);


    void cleanupExpiredRequests();
}
