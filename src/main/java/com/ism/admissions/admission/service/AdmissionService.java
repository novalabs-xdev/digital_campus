package com.ism.admissions.admission.service;

import com.ism.admissions.admission.domain.Admission;

public interface AdmissionService {
    Admission creerAdmission(Long candidatureId);
}
