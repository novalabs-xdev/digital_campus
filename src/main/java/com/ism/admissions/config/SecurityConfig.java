package com.ism.admissions.config;

import com.ism.admissions.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. CSRF désactivé (JWT stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Session stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Autorisation des routes
                .authorizeHttpRequests(auth -> auth

                        // ---------- ROUTES PUBLIQUES ----------
                        .requestMatchers(
                                "/",
                                "/ws/**",
                                "/api/v1/auth/**",
                                "/api/v1/health",
                                "/api/v1/webhooks/**",
                                "/favicon.ico",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api-docs/**"
                        ).permitAll()

                        // ---------- ADMINISTRATEUR ----------
                        .requestMatchers("/api/**").hasRole("ADMIN")

                        // ---------- CANDIDATS ----------
                        .requestMatchers(HttpMethod.POST, "/api/v1/candidats").permitAll()
                        .requestMatchers("/api/v1/candidats/**").authenticated()

                        // ---------- ÉTUDIANT ----------
                        .requestMatchers(
                                "/api/v1/candidatures/*/documents",
                                "/api/v1/candidatures/*/documents/**",
                                "/api/v1/candidatures/*/soumettre"
                        ).hasAnyRole("ETUDIANT", "ADMIN")

                        // ---------- AGENT ----------
                        .requestMatchers(
                                "/api/v1/candidatures/*/pre-valider"
                        ).hasAnyRole("AGENT", "ADMIN")

                        // ---------- DIRECTEUR ----------
                        .requestMatchers(
                                "/api/v1/candidatures/*/valider",
                                "/api/v1/candidatures/*/rejeter",
                                "/api/v1/admissions/**"
                        ).hasAnyRole("DIRECTEUR", "ADMIN")

                        // ---------- HISTORIQUE ----------
                        .requestMatchers(
                                "/api/v1/candidatures/*/historique"
                        ).hasAnyRole("ETUDIANT", "AGENT", "DIRECTEUR", "ADMIN")

                        // ---------- PAR DÉFAUT ----------
                        .anyRequest().authenticated()
                )

                // 5. Filtre JWT
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Autorise le front Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8080"));

        // Méthodes autorisées
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Headers autorisés (ajouts pour la stabilité)
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}