package com.ism.admissions.user.dto;

import com.ism.admissions.user.domain.Role;

public record UserResponse(
        Long id,
        String email,
        String fullName,
        Role role
) {
}
