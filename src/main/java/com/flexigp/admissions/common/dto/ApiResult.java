package com.flexigp.admissions.common.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiResult<T>(
        int status,
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp
) {
    // Méthode utilitaire pour les succès
    public static <T> ApiResult<T> success(T data, String message) {
        return ApiResult.<T>builder()
                .status(200)
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Méthode utilitaire pour les succès sans données (Void)
    public static ApiResult<Void> success(String message) {
        return ApiResult.<Void>builder()
                .status(200)
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // --- LA MÉTHODE ERREUR ADAPTÉE ---
    public static <T> ApiResult<T> error(String message) {
        return ApiResult.<T>builder()
                .status(400) // Par défaut 400 pour les erreurs métier
                .success(false)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Optionnel : Une variante pour préciser le code d'erreur (403, 404, etc.)
    public static <T> ApiResult<T> error(int status, String message) {
        return ApiResult.<T>builder()
                .status(status)
                .success(false)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
