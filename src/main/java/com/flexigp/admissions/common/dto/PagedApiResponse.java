package com.flexigp.admissions.common.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PagedApiResponse<T>(
        int status,
        boolean success,
        String message,
        List<T> data,
        PaginationMeta pagination,
        LocalDateTime timestamp
) {
}
