package com.flexigp.admissions.auth.passwordreset.repository;

import com.flexigp.admissions.auth.passwordreset.domain.PasswordResetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, Long> {

    Optional<PasswordResetRequest> findTopByEmailOrderByCreatedAtDesc(String email);

    Optional<PasswordResetRequest> findByToken(String token);

    @Modifying
    @Query("DELETE FROM PasswordResetRequest p WHERE p.email = :email")
    void deleteByEmail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM PasswordResetRequest p WHERE p.otpExpiresAt < :now OR p.tokenExpiresAt < :now OR p.used = true")
    void deleteAllExpiredOrUsed(@Param("now") LocalDateTime now);
}
