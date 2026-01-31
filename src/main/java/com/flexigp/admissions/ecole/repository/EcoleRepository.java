package com.flexigp.admissions.ecole.repository;

import com.flexigp.admissions.ecole.domain.Ecole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcoleRepository extends JpaRepository<Ecole, Long> {
}
