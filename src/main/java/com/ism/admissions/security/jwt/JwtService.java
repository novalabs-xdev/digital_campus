package com.ism.admissions.security.jwt;

import com.ism.admissions.user.domain.User;

public interface JwtService {
    String generateToken(User user);

    boolean isTokenValid(String token);

    String extractUsername(String token);
}
