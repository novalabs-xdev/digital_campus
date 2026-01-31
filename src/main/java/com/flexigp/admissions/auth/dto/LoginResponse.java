package com.flexigp.admissions.auth.dto;

import com.flexigp.admissions.user.domain.Role;

public record LoginResponse(
        String token,
        String email,
        Role role
) {
}
