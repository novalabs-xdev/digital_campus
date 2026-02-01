package com.ism.admissions.admission.service;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.dto.StatistiquesClasseResponse;
import com.ism.admissions.admission.exception.AdmissionAlreadyExistsException;
import com.ism.admissions.admission.exception.CandidatureNotValideeException;
import com.ism.admissions.admission.generator.AdmissionNumberGenerator;
import com.ism.admissions.admission.repository.AdmissionRepository;
import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.candidature.exception.CandidatureNotFoundException;
import com.ism.admissions.candidature.repository.CandidatureRepository;
import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.classe.repository.ClasseRepository;
import com.ism.admissions.exception.business.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final CandidatureRepository candidatureRepository;
    private final ClasseRepository classeRepository;
    private final AdmissionNumberGenerator admissionNumberGenerator;

    @Override
    public Admission creerAdmission(Long candidatureId) {

        if (admissionRepository.existsByCandidatureId(candidatureId)) {
            throw new AdmissionAlreadyExistsException(candidatureId);
        }

        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() ->
                        new CandidatureNotFoundException(candidatureId)
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

    @Override
    @Transactional(readOnly = true)
    public List<Admission> listerAdmissions(Long ecoleId, Long classeId) {
        return admissionRepository.findWithFilters(ecoleId, classeId);
    }

    @Override
    @Transactional(readOnly = true)
    public StatistiquesClasseResponse getStatistiquesClasse(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new ResourceNotFoundException("Classe introuvable (id=" + classeId + ")"));

        List<Admission> admissions = admissionRepository.findByCandidatureClasseId(classeId);
        int nombreAdmis = admissions.size();
        int capacite = classe.getCapacite() != null ? classe.getCapacite() : 0;
        int placesRestantes = Math.max(0, capacite - nombreAdmis);
        double tauxRemplissage = capacite > 0 ? (double) nombreAdmis / capacite * 100 : 0;

        return new StatistiquesClasseResponse(
                classe.getId(),
                classe.getLibelle(),
                classe.getCapacite(),
                nombreAdmis,
                placesRestantes,
                Math.round(tauxRemplissage * 100.0) / 100.0
        );
    }
}
