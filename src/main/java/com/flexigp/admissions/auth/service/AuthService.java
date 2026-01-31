package com.flexigp.admissions.auth.service;

import com.flexigp.admissions.auth.domain.AuthResult;
import com.flexigp.admissions.auth.dto.LoginRequest;
import com.flexigp.admissions.auth.dto.RegisterRequest;
import com.flexigp.admissions.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    User register(RegisterRequest request);
    AuthResult login(LoginRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
