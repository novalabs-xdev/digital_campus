package com.ism.admissions.admission.dto;

import com.ism.admissions.candidat.dto.CandidatResponse;
import com.ism.admissions.classe.dto.ClasseResponse;
import com.ism.admissions.ecole.dto.EcoleResponse;

import java.time.LocalDateTime;

public record AdmissionResponse(
        Long id,
        String numeroAdmission,
        CandidatResponse candidat,
        EcoleResponse ecole,
        ClasseResponse classe,
        LocalDateTime dateAdmission
) {
}
