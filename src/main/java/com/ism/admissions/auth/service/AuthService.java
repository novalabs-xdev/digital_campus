package com.ism.admissions.auth.service;

import com.ism.admissions.auth.domain.AuthResult;
import com.ism.admissions.auth.dto.LoginRequest;
import com.ism.admissions.auth.dto.RegisterRequest;
import com.ism.admissions.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    User register(RegisterRequest request);
    AuthResult login(LoginRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
