package com.ism.admissions.reporting.dto;

import java.util.Map;

public record DashboardStatsResponse(
        int totalCandidatures,
        int candidaturesEnAttente,
        int candidaturesValidees,
        int candidaturesRejetees,
        int totalAdmissions,
        Map<String, Integer> admissionsParEcole,
        Map<String, Integer> candidaturesParStatut
) {
}