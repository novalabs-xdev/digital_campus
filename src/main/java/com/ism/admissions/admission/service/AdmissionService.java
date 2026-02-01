package com.ism.admissions.admission.service;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.dto.StatistiquesClasseResponse;

import java.util.List;

public interface AdmissionService {
    Admission creerAdmission(Long candidatureId);

    List<Admission> listerAdmissions(Long ecoleId, Long classeId);

    StatistiquesClasseResponse getStatistiquesClasse(Long classeId);
}
