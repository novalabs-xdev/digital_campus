package com.ism.admissions.candidature.repository;

import com.ism.admissions.candidature.domain.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

}
