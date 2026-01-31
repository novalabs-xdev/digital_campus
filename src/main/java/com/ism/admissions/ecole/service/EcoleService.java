package com.ism.admissions.ecole.service;

import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.ecole.dto.EcoleCreateRequest;
import com.ism.admissions.ecole.dto.EcoleUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface EcoleService {
    Ecole creerEcole(EcoleCreateRequest request);

    List<Ecole> listerEcoles();

    Optional<Ecole> getEcole(Long id);

    Ecole modifierEcole(Long id, EcoleUpdateRequest request);

    void desactiverEcole(Long id);
}
