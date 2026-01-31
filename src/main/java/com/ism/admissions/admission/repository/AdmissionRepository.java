package com.ism.admissions.admission.repository;


import com.ism.admissions.admission.domain.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    Optional<Admission> findByCandidatureId(Long candidatureId);

    boolean existsByCandidatureId(Long candidatureId);

    List<Admission> findByCandidatureClasseId(Long classeId);

    @Query("SELECT COUNT(a) FROM Admission a WHERE a.numeroAdmission LIKE CONCAT('AD-', :annee, '-', :codeEcole, '-%')")
    long countByAnneeAndEcole(@Param("annee") String annee, @Param("codeEcole") String codeEcole);
}