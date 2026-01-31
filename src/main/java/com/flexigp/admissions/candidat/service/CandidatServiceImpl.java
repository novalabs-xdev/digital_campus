package com.flexigp.admissions.candidat.service;

import com.flexigp.admissions.candidat.domain.Candidat;
import com.flexigp.admissions.candidat.exception.CandidatDoublonException;
import com.flexigp.admissions.candidat.repository.CandidatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidatServiceImpl implements CandidatService {
    private final CandidatRepository candidatRepository;

    @Override
    public Candidat creerCandidat(Candidat candidat) {
        System.out.println(candidat);
        boolean isExist = candidatRepository.existsByEmail(candidat.getEmail());
        if (isExist) {
            throw new CandidatDoublonException("Candidat avec cet email existe déjà");
        }
        return candidatRepository.save(candidat);
    }

    @Override
    public Optional<Candidat> rechercherParEmail(String email) {
        return candidatRepository.findByEmail(email);
    }
}
