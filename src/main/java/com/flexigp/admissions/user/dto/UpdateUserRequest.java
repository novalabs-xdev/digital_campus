package com.flexigp.admissions.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank
        String fullName,

        @NotBlank
        String phone,

        @NotBlank
        String country
) {
}
