package com.flexigp.admissions.candidat.repository;

import com.flexigp.admissions.candidat.domain.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    Optional<Candidat> findByEmail(String email);
    boolean existsByEmail(String email);
}
