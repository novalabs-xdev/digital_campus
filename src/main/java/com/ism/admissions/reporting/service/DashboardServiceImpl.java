package com.ism.admissions.reporting.service;

import com.ism.admissions.admission.repository.AdmissionRepository;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.candidature.repository.CandidatureRepository;
import com.ism.admissions.reporting.dto.DashboardStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CandidatureRepository candidatureRepository;
    private final AdmissionRepository admissionRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsResponse getStatistiquesGlobales() {
        long totalCandidatures = candidatureRepository.count();
        long enAttente = candidatureRepository.countByStatut(StatutCandidature.SOUMISE)
                + candidatureRepository.countByStatut(StatutCandidature.A_VALIDER)
                + candidatureRepository.countByStatut(StatutCandidature.EN_ATTENTE);
        long validees = candidatureRepository.countByStatut(StatutCandidature.VALIDEE);
        long rejetees = candidatureRepository.countByStatut(StatutCandidature.REJETEE);
        long totalAdmissions = admissionRepository.count();

        Map<String, Integer> candidaturesParStatut = new HashMap<>();
        for (StatutCandidature statut : StatutCandidature.values()) {
            candidaturesParStatut.put(statut.name(), (int) candidatureRepository.countByStatut(statut));
        }

        Map<String, Integer> admissionsParEcole = new HashMap<>();
        admissionRepository.findAll().forEach(admission -> {
            String ecoleNom = admission.getCandidature().getEcole().getNom();
            admissionsParEcole.merge(ecoleNom, 1, Integer::sum);
        });

        return new DashboardStatsResponse(
                (int) totalCandidatures,
                (int) enAttente,
                (int) validees,
                (int) rejetees,
                (int) totalAdmissions,
                admissionsParEcole,
                candidaturesParStatut
        );
    }
}
