package com.ism.admissions.classe.service;

import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.classe.dto.ClasseCreateRequest;
import com.ism.admissions.classe.dto.ClasseUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ClasseService {
    Classe creerClasse(ClasseCreateRequest request);

    List<Classe> listerClassesParEcole(Long ecoleId);

    Optional<Classe> getClasse(Long id);

    Classe modifierClasse(Long id, ClasseUpdateRequest request);

    void supprimerClasse(Long id);
}