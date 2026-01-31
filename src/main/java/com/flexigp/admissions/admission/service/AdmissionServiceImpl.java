package com.flexigp.admissions.admission.service;

import com.flexigp.admissions.admission.Admission;
import com.flexigp.admissions.admission.exception.AdmissionAlreadyExistsException;
import com.flexigp.admissions.admission.exception.CandidatureNotValideeException;
import com.flexigp.admissions.admission.generator.AdmissionNumberGenerator;
import com.flexigp.admissions.admission.repository.AdmissionRepository;
import com.flexigp.admissions.candidature.domain.Candidature;
import com.flexigp.admissions.candidature.domain.StatutCandidature;
import com.flexigp.admissions.candidature.repository.CandidatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final CandidatureRepository candidatureRepository;
    private final AdmissionNumberGenerator admissionNumberGenerator;

    @Override
    public Admission creerAdmission(Long candidatureId) {

        if (admissionRepository.existsByCandidatureId(candidatureId)) {
            throw new AdmissionAlreadyExistsException(candidatureId);
        }

        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() ->
                        new RuntimeException("Candidature introuvable id=" + candidatureId)
                );

        if (candidature.getStatut() != StatutCandidature.VALIDEE) {
            throw new CandidatureNotValideeException(candidatureId);
        }

        Admission admission = Admission.builder()
                .candidature(candidature)
                .dateAdmission(LocalDateTime.now())
                .build();

        String numero = admissionNumberGenerator.genererNumero(candidature);
        admission.setNumeroAdmission(numero);
        return admissionRepository.save(admission);
    }

}
