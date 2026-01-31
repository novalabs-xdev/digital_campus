package com.ism.admissions.classe.repository;

import com.ism.admissions.classe.domain.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    List<Classe> findByEcoleId(Long id);
}
