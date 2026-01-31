package com.flexigp.admissions.admission.repository;


import com.flexigp.admissions.admission.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    Optional<Admission> findByCandidatureId(Long candidatureId);

    boolean existsByCandidatureId(Long candidatureId);
}