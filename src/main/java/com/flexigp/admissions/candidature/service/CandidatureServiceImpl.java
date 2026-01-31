package com.flexigp.admissions.candidature.service;

import com.flexigp.admissions.candidature.domain.Candidature;
import com.flexigp.admissions.candidature.domain.StatutCandidature;
import com.flexigp.admissions.candidature.exception.CandidatureNotFoundException;
import com.flexigp.admissions.candidature.exception.CandidatureStatutInvalideException;
import com.flexigp.admissions.candidature.exception.DocumentsManquantsException;
import com.flexigp.admissions.candidature.repository.CandidatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidatureServiceImpl implements CandidatureService {
    private final CandidatureRepository candidatureRepository;

    @Override
    public void soumettreCandidature(Long candidatureId) {

        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new CandidatureNotFoundException(candidatureId));

        if (candidature.getStatut() != StatutCandidature.BROUILLON) {
            throw new CandidatureStatutInvalideException(
                    candidature.getStatut(),
                    "soumettre"
            );
        }

        if (candidature.getDocuments() == null || candidature.getDocuments().isEmpty()) {
            throw new DocumentsManquantsException();
        }

        candidature.setStatut(StatutCandidature.SOUMISE);
        candidatureRepository.save(candidature);

    }

    @Override
    public void preValiderParAgent(Long candidatureId) {

        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (candidature.getStatut() != StatutCandidature.SOUMISE) {
            throw new RuntimeException(
                    "Seule une candidature soumise peut être pré-validée"
            );
        }

        candidature.setStatut(StatutCandidature.A_VALIDER);
        candidatureRepository.save(candidature);
    }

    @Override
    public void validerParDirecteur(Long candidatureId) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (candidature.getStatut() != StatutCandidature.A_VALIDER) {
            throw new RuntimeException(
                    "Seule une candidature pré-validée peut être validée"
            );
        }

        candidature.setStatut(StatutCandidature.VALIDEE);
        candidatureRepository.save(candidature);
    }

    @Override
    public void rejeterParDirecteur(Long candidatureId, String motif) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (candidature.getStatut() != StatutCandidature.A_VALIDER) {
            throw new RuntimeException(
                    "Seule une candidature pré-validée peut être rejetée"
            );
        }

        candidature.setStatut(StatutCandidature.REJETEE);

        // 🔜 Plus tard : stocker le motif dans l’entité
        candidatureRepository.save(candidature);
    }
}
