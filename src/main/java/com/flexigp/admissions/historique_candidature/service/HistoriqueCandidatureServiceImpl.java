package com.flexigp.admissions.historique_candidature.service;

import com.flexigp.admissions.candidature.domain.Candidature;
import com.flexigp.admissions.candidature.domain.StatutCandidature;
import com.flexigp.admissions.historique_candidature.domain.HistoriqueStatutCandidature;
import com.flexigp.admissions.historique_candidature.repository.HistoriqueStatutCandidatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueCandidatureServiceImpl implements HistoriqueCandidatureService {
    private final HistoriqueStatutCandidatureRepository repository;

    @Override
    public void enregistrerTransition(
            Candidature candidature,
            StatutCandidature ancienStatut,
            StatutCandidature nouveauStatut,
            String action,
            String effectuePar
    ) {
        HistoriqueStatutCandidature historique =
                HistoriqueStatutCandidature.builder()
                        .candidature(candidature)
                        .ancienStatut(ancienStatut)
                        .nouveauStatut(nouveauStatut)
                        .action(action)
                        .dateAction(LocalDateTime.now())
                        .effectuePar(effectuePar)
                        .build();

        repository.save(historique);
    }

    @Override
    public List<HistoriqueStatutCandidature> consulterHistorique(Long candidatureId) {
        return repository.findByCandidatureIdOrderByDateActionAsc(candidatureId);
    }
}
