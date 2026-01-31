package com.flexigp.admissions.user.dto;

import com.flexigp.admissions.user.domain.Role;

import java.time.LocalDateTime;

public record UserProfileResponse(
        Long id,
        String email,
        String fullName,
        String phone,
        String country,
        Role role,
        LocalDateTime createdAt
) {
}
