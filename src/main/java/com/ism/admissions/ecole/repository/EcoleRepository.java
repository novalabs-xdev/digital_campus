package com.ism.admissions.ecole.repository;

import com.ism.admissions.ecole.domain.Ecole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcoleRepository extends JpaRepository<Ecole, Long> {
}
