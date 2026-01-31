package com.ism.admissions.candidature.dto;

import java.time.LocalDateTime;

public record DocumentResponse(
        Long id,
        String typeDocument,
        String nomOriginal,
        String storageKey,
        LocalDateTime dateUpload
) {
}
