package com.ism.admissions.reporting.controller;

import com.ism.admissions.common.dto.ApiResult;
import com.ism.admissions.reporting.dto.DashboardStatsResponse;
import com.ism.admissions.reporting.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardControllerImpl implements DashboardController {

    private final DashboardService dashboardService;

    @Override
    public ResponseEntity<ApiResult<DashboardStatsResponse>> getStatistiquesGlobales() {
        DashboardStatsResponse stats = dashboardService.getStatistiquesGlobales();
        return ResponseEntity.ok(ApiResult.success(
                stats,
                "Statistiques globales récupérées avec succès"
        ));
    }
}
