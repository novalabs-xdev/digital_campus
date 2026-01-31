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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceImplTest {

    @Mock private PasswordResetRequestRepository repository;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MailService emailService;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetService;

    // --- forgotPassword ---

    @Test
    void forgotPassword_existingUser_sendsOtp() {
        User user = new User();
        user.setEmail("user@ism.sn");
        when(userRepository.findByEmail("user@ism.sn")).thenReturn(Optional.of(user));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        passwordResetService.forgotPassword(new ForgotPasswordRequest("user@ism.sn"));

        verify(repository).deleteByEmail("user@ism.sn");
        verify(repository).save(any(PasswordResetRequest.class));
        verify(emailService).sendPasswordResetOtp(eq("user@ism.sn"), anyString());
    }

    @Test
    void forgotPassword_unknownEmail_doesNothing() {
        when(userRepository.findByEmail("unknown@ism.sn")).thenReturn(Optional.empty());

        passwordResetService.forgotPassword(new ForgotPasswordRequest("unknown@ism.sn"));

        verify(repository, never()).save(any());
        verify(emailService, never()).sendPasswordResetOtp(anyString(), anyString());
    }

    // --- verifyOtp ---

    @Test
    void verifyOtp_validOtp_returnsToken() {
        PasswordResetRequest resetRequest = PasswordResetRequest.builder()
                .email("user@ism.sn")
                .otp("123456")
                .otpExpiresAt(LocalDateTime.now().plusMinutes(10))
                .otpVerified(false)
                .used(false)
                .build();

        when(repository.findTopByEmailOrderByCreatedAtDesc("user@ism.sn"))
                .thenReturn(Optional.of(resetRequest));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        VerifyOtpResponse response = passwordResetService.verifyOtp(new VerifyOtpRequest("user@ism.sn", "123456"));

        assertNotNull(response.token());
        assertTrue(resetRequest.isOtpVerified());
        assertNotNull(resetRequest.getTokenExpiresAt());
    }

    @Test
    void verifyOtp_expiredOtp_throws() {
        PasswordResetRequest resetRequest = PasswordResetRequest.builder()
                .email("user@ism.sn")
                .otp("123456")
                .otpExpiresAt(LocalDateTime.now().minusMinutes(1))
                .build();

        when(repository.findTopByEmailOrderByCreatedAtDesc("user@ism.sn"))
                .thenReturn(Optional.of(resetRequest));

        assertThrows(BadRequestException.class,
                () -> passwordResetService.verifyOtp(new VerifyOtpRequest("user@ism.sn", "123456")));
    }

    @Test
    void verifyOtp_wrongOtp_throws() {
        PasswordResetRequest resetRequest = PasswordResetRequest.builder()
                .email("user@ism.sn")
                .otp("123456")
                .otpExpiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        when(repository.findTopByEmailOrderByCreatedAtDesc("user@ism.sn"))
                .thenReturn(Optional.of(resetRequest));

        assertThrows(BadRequestException.class,
                () -> passwordResetService.verifyOtp(new VerifyOtpRequest("user@ism.sn", "000000")));
    }

    @Test
    void verifyOtp_noRequest_throws() {
        when(repository.findTopByEmailOrderByCreatedAtDesc("user@ism.sn"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passwordResetService.verifyOtp(new VerifyOtpRequest("user@ism.sn", "123456")));
    }

    // --- resetPassword ---

    @Test
    void resetPassword_validToken_updatesPassword() {
        String token = "valid-token";
        PasswordResetRequest resetRequest = PasswordResetRequest.builder()
                .email("user@ism.sn")
                .token(token)
                .tokenExpiresAt(LocalDateTime.now().plusMinutes(20))
                .otpVerified(true)
                .used(false)
                .build();

        User user = new User();
        user.setEmail("user@ism.sn");
        user.setPassword("old-hash");

        when(repository.findByToken(token)).thenReturn(Optional.of(resetRequest));
        when(userRepository.findByEmail("user@ism.sn")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword123")).thenReturn("new-hash");

        passwordResetService.resetPassword(new ResetPasswordRequest(token, "newPassword123"));

        assertEquals("new-hash", user.getPassword());
        verify(userRepository).save(user);
        verify(repository).deleteByEmail("user@ism.sn");
    }

    @Test
    void resetPassword_expiredToken_throws() {
        String token = "expired-token";
        PasswordResetRequest resetRequest = PasswordResetRequest.builder()
                .email("user@ism.sn")
                .token(token)
                .tokenExpiresAt(LocalDateTime.now().minusMinutes(1))
                .otpVerified(true)
                .used(false)
                .build();

        when(repository.findByToken(token)).thenReturn(Optional.of(resetRequest));

        assertThrows(BadRequestException.class,
                () -> passwordResetService.resetPassword(new ResetPasswordRequest(token, "newPass")));
    }

    @Test
    void resetPassword_alreadyUsed_throws() {
        String token = "used-token";
        PasswordResetRequest resetRequest = PasswordResetRequest.builder()
                .token(token)
                .tokenExpiresAt(LocalDateTime.now().plusMinutes(20))
                .otpVerified(true)
                .used(true)
                .build();

        when(repository.findByToken(token)).thenReturn(Optional.of(resetRequest));

        assertThrows(BadRequestException.class,
                () -> passwordResetService.resetPassword(new ResetPasswordRequest(token, "newPass")));
    }
}
