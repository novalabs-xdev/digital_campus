package com.ism.admissions.auth.dto;

import com.ism.admissions.user.domain.Role;

public record LoginResponse(
        String token,
        String email,
        Role role
) {
}
