package com.flexigp.admissions.auth.service;

import com.flexigp.admissions.auth.domain.AuthResult;
import com.flexigp.admissions.auth.dto.LoginRequest;
import com.flexigp.admissions.auth.dto.RegisterRequest;
import com.flexigp.admissions.exception.business.EmailAlreadyUsedException;
import com.flexigp.admissions.exception.business.InvalidCredentialsException;
import com.flexigp.admissions.mail.service.MailService;
import com.flexigp.admissions.security.jwt.JwtService;
import com.flexigp.admissions.user.domain.User;
import com.flexigp.admissions.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;

    @Override
    public User register(RegisterRequest request) {
        if (userService.existsByEmail(request.email())) {
            throw new EmailAlreadyUsedException(request.email());
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        // Logique de rôle


        User savedUser = userService.createUser(user);

        // Envoi de l'email (asynchrone)
        mailService.sendRegistrationEmail(
                savedUser.getEmail(),
                null,
                null,
                null,
                request.password()
        );

        return savedUser;
    }

    @Override
    public AuthResult login(LoginRequest request) {

        User user = userService.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String token = jwtService.generateToken(user);

        return new AuthResult(
                user,
                token
        );
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Supprimer tous les cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }

        // Nettoyer le contexte Spring Security
        SecurityContextHolder.clearContext();
    }
}

