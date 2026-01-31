package com.flexigp.admissions.candidature.repository;

import com.flexigp.admissions.candidature.domain.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

}
