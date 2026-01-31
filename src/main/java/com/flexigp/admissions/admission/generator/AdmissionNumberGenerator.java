package com.flexigp.admissions.admission.generator;

import com.flexigp.admissions.candidature.domain.Candidature;

public interface AdmissionNumberGenerator {
    String genererNumero(Candidature candidature);
}
