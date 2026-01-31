package com.flexigp.admissions.auth.domain;

import com.flexigp.admissions.user.domain.User;

public record AuthResult(
        User user,
        String token
) {
}
