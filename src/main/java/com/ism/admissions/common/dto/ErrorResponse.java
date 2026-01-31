package com.ism.admissions.common.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponse(
        int status,
        String error,
        String message,
        Map<String, String> errors,
        String path,
        LocalDateTime timestamp
) {
}
