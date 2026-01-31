package com.flexigp.admissions.security.jwt;

import com.flexigp.admissions.user.domain.User;

public interface JwtService {
    String generateToken(User user);

    boolean isTokenValid(String token);

    String extractUsername(String token);
}
