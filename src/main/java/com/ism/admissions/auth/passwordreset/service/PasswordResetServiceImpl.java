package com.ism.admissions.auth.passwordreset.service;

import com.ism.admissions.auth.passwordreset.domain.PasswordResetRequest;
import com.ism.admissions.auth.passwordreset.dto.ForgotPasswordRequest;
import com.ism.admissions.auth.passwordreset.dto.ResetPasswordRequest;
import com.ism.admissions.auth.passwordreset.dto.VerifyOtpRequest;
import com.ism.admissions.auth.passwordreset.dto.VerifyOtpResponse;
import com.ism.admissions.auth.passwordreset.repository.PasswordResetRequestRepository;
import com.ism.admissions.exception.business.BadRequestException;
import com.ism.admissions.exception.business.ResourceNotFoundException;
import com.ism.admissions.mail.service.MailService;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PasswordResetServiceImpl implements PasswordResetService {

    private final PasswordResetRequestRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.password-reset.otp-expiration:15}")
    private int otpExpiryMinutes;

    @Value("${app.password-reset.token-expiration:30}")
    private int tokenExpiryMinutes;

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            repository.deleteByEmail(user.getEmail());

            String otp = generateSecureOtp();
            PasswordResetRequest resetRequest = createResetRequest(user.getEmail(), otp);

            repository.save(resetRequest);
            emailService.sendPasswordResetOtp(user.getEmail(), otp);

            log.info("Processus de réinitialisation initié pour: {}", user.getEmail());
        });
    }

    @Override
    @Transactional
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        PasswordResetRequest resetRequest = repository.findTopByEmailOrderByCreatedAtDesc(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune demande trouvée pour cet email"));

        validateOtp(resetRequest, request.otp());

        String token = UUID.randomUUID().toString();
        resetRequest.setOtpVerified(true);
        resetRequest.setToken(token);
        resetRequest.setTokenExpiresAt(LocalDateTime.now().plusMinutes(tokenExpiryMinutes));

        repository.save(resetRequest);
        return new VerifyOtpResponse(token, "OTP validé");
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetRequest resetRequest = repository.findByToken(request.token())
                .filter(req -> !req.isUsed() && req.isOtpVerified())
                .filter(req -> req.getTokenExpiresAt().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new BadRequestException("Token invalide ou expiré"));

        User user = userRepository.findByEmail(resetRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        resetRequest.setUsed(true);
        repository.deleteByEmail(user.getEmail()); // Nettoyage immédiat après succès
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 * * * *") // Nettoyage toutes les heures
    public void cleanupExpiredRequests() {
        repository.deleteAllExpiredOrUsed(LocalDateTime.now());
        log.info("Nettoyage des requêtes de réinitialisation terminé");
    }

    // --- Helper Methods (Private) ---

    private String generateSecureOtp() {
        return String.format("%06d", secureRandom.nextInt(1000000));
    }

    private PasswordResetRequest createResetRequest(String email, String otp) {
        return PasswordResetRequest.builder()
                .email(email)
                .otp(otp)
                .otpExpiresAt(LocalDateTime.now().plusMinutes(otpExpiryMinutes))
                .otpVerified(false)
                .used(false)
                .build();
    }

    private void validateOtp(PasswordResetRequest request, String providedOtp) {
        if (request.getOtpExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP expiré");
        }
        if (!request.getOtp().equals(providedOtp)) {
            throw new BadRequestException("OTP incorrect");
        }
    }
}
