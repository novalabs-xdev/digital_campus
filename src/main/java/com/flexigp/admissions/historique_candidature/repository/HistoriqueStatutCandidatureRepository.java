package com.flexigp.admissions.historique_candidature.repository;

import com.flexigp.admissions.historique_candidature.domain.HistoriqueStatutCandidature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueStatutCandidatureRepository
        extends JpaRepository<HistoriqueStatutCandidature, Long> {

    List<HistoriqueStatutCandidature> findByCandidatureIdOrderByDateActionAsc(Long candidatureId);
}
