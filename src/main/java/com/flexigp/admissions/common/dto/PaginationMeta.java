package com.flexigp.admissions.common.dto;

import lombok.Builder;

@Builder
public record PaginationMeta(
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
}
