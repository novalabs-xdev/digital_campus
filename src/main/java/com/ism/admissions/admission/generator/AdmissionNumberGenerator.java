package com.ism.admissions.admission.generator;

import com.ism.admissions.candidature.domain.Candidature;

public interface AdmissionNumberGenerator {
    String genererNumero(Candidature candidature);
}
