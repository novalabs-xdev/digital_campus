package com.flexigp.admissions.user.dto;

import com.flexigp.admissions.user.domain.Role;

public record UserResponse(
        Long id,
        String email,
        String fullName,
        Role role
) {
}
