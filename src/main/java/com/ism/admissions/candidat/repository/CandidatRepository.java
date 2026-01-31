package com.ism.admissions.candidat.repository;

import com.ism.admissions.candidat.domain.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    Optional<Candidat> findByEmail(String email);
    boolean existsByEmail(String email);
}
