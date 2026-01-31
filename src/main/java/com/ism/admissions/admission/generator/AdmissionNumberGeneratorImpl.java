package com.ism.admissions.admission.generator;

import com.ism.admissions.admission.repository.AdmissionRepository;
import com.ism.admissions.candidature.domain.Candidature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@RequiredArgsConstructor
public class AdmissionNumberGeneratorImpl implements AdmissionNumberGenerator {

    private final AdmissionRepository admissionRepository;

    @Override
    public String genererNumero(Candidature candidature) {
        String annee = Year.now().toString();
        String codeEcole = candidature.getEcole().getCode();

        long count = admissionRepository.countByAnneeAndEcole(annee, codeEcole);
        long seq = count + 1;

        return String.format("AD-%s-%s-%06d", annee, codeEcole, seq);
    }
}
