package com.ism.admissions.reporting.controller;

import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.reporting.dto.DashboardStatsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Dashboard")
@RequestMapping("/api/v1/dashboard")
public interface DashboardController {

    @GetMapping("/stats")
    ResponseEntity<ApiResult<DashboardStatsResponse>> getStatistiquesGlobales();
}