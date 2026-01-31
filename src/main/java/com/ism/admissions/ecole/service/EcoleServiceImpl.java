package com.ism.admissions.ecole.service;

import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.ecole.dto.EcoleCreateRequest;
import com.ism.admissions.ecole.dto.EcoleUpdateRequest;
import com.ism.admissions.ecole.mapper.EcoleMapper;
import com.ism.admissions.ecole.repository.EcoleRepository;
import com.ism.admissions.exception.business.BadRequestException;
import com.ism.admissions.exception.business.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EcoleServiceImpl implements EcoleService {

    private final EcoleRepository ecoleRepository;
    private final EcoleMapper ecoleMapper;

    @Override
    @Transactional
    public Ecole creerEcole(EcoleCreateRequest request) {
        // Vérifier que le code n'existe pas déjà
        if (ecoleRepository.existsByCode(request.code())) {
            throw new BadRequestException("Une école avec ce code existe déjà");
        }

        Ecole ecole = ecoleMapper.toEntity(request);
        return ecoleRepository.save(ecole);
    }

    @Override
    public List<Ecole> listerEcoles() {
        return ecoleRepository.findAll();
    }

    @Override
    public Optional<Ecole> getEcole(Long id) {
        return ecoleRepository.findById(id);
    }

    @Override
    @Transactional
    public Ecole modifierEcole(Long id, EcoleUpdateRequest request) {
        Ecole ecole = ecoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("École introuvable"));

        ecole.setNom(request.nom());
        ecole.setActif(request.actif());

        return ecoleRepository.save(ecole);
    }

    @Override
    @Transactional
    public void desactiverEcole(Long id) {
        Ecole ecole = ecoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("École introuvable"));

        ecole.setActif(false);
        ecoleRepository.save(ecole);
    }
}
