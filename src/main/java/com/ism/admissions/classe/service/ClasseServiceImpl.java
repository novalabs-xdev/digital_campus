package com.ism.admissions.classe.service;

import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.classe.dto.ClasseCreateRequest;
import com.ism.admissions.classe.dto.ClasseUpdateRequest;
import com.ism.admissions.classe.mapper.ClasseMapper;
import com.ism.admissions.classe.repository.ClasseRepository;
import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.ecole.repository.EcoleRepository;
import com.ism.admissions.exception.business.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;
    private final EcoleRepository ecoleRepository;
    private final ClasseMapper classeMapper;

    @Override
    @Transactional
    public Classe creerClasse(ClasseCreateRequest request) {
        Ecole ecole = ecoleRepository.findById(request.ecoleId())
                .orElseThrow(() -> new ResourceNotFoundException("École introuvable"));

        Classe classe = classeMapper.toEntity(request);
        classe.setEcole(ecole);

        return classeRepository.save(classe);
    }

    @Override
    public List<Classe> listerClassesParEcole(Long ecoleId) {
        return classeRepository.findByEcoleId(ecoleId);
    }

    @Override
    public Optional<Classe> getClasse(Long id) {
        return classeRepository.findById(id);
    }

    @Override
    @Transactional
    public Classe modifierClasse(Long id, ClasseUpdateRequest request) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe introuvable"));

        classe.setLibelle(request.libelle());
        classe.setCapacite(request.capacite());
        classe.setActif(request.actif());

        return classeRepository.save(classe);
    }

    @Override
    @Transactional
    public void supprimerClasse(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe introuvable"));

        classe.setActif(false);
        classeRepository.save(classe);
    }
}