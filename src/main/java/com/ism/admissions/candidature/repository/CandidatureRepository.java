package com.ism.admissions.candidature.repository;

import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.StatutCandidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    @Query("SELECT c FROM Candidature c WHERE " +
            "(:statut IS NULL OR c.statut = :statut) AND " +
            "(:ecoleId IS NULL OR c.ecole.id = :ecoleId) AND " +
            "(:classeId IS NULL OR c.classe.id = :classeId)")
    List<Candidature> findWithFilters(
            @Param("statut") StatutCandidature statut,
            @Param("ecoleId") Long ecoleId,
            @Param("classeId") Long classeId
    );

    long countByStatut(StatutCandidature statut);
}
