package com.ism.admissions.auth.domain;

import com.ism.admissions.user.domain.User;

public record AuthResult(
        User user,
        String token
) {
}
