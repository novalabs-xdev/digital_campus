package com.ism.admissions.admission.service;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.exception.AdmissionAlreadyExistsException;
import com.ism.admissions.admission.exception.CandidatureNotValideeException;
import com.ism.admissions.admission.generator.AdmissionNumberGenerator;
import com.ism.admissions.admission.repository.AdmissionRepository;
import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.candidature.exception.CandidatureNotFoundException;
import com.ism.admissions.candidature.repository.CandidatureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdmissionServiceImplTest {

    @Mock private AdmissionRepository admissionRepository;
    @Mock private CandidatureRepository candidatureRepository;
    @Mock private AdmissionNumberGenerator admissionNumberGenerator;

    @InjectMocks
    private AdmissionServiceImpl admissionService;

    @Test
    void creerAdmission_success() {
        Candidature candidature = new Candidature();
        candidature.setId(1L);
        candidature.setStatut(StatutCandidature.VALIDEE);

        when(admissionRepository.existsByCandidatureId(1L)).thenReturn(false);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        when(admissionNumberGenerator.genererNumero(candidature)).thenReturn("AD-2026-ISM-000001");
        when(admissionRepository.save(any(Admission.class))).thenAnswer(inv -> inv.getArgument(0));

        Admission result = admissionService.creerAdmission(1L);

        assertEquals("AD-2026-ISM-000001", result.getNumeroAdmission());
        assertEquals(candidature, result.getCandidature());
        assertNotNull(result.getDateAdmission());
    }

    @Test
    void creerAdmission_alreadyExists_throws() {
        when(admissionRepository.existsByCandidatureId(1L)).thenReturn(true);
        assertThrows(AdmissionAlreadyExistsException.class, () -> admissionService.creerAdmission(1L));
    }

    @Test
    void creerAdmission_candidatureNotFound_throws() {
        when(admissionRepository.existsByCandidatureId(1L)).thenReturn(false);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CandidatureNotFoundException.class, () -> admissionService.creerAdmission(1L));
    }

    @Test
    void creerAdmission_candidatureNotValidee_throws() {
        Candidature candidature = new Candidature();
        candidature.setId(1L);
        candidature.setStatut(StatutCandidature.SOUMISE);

        when(admissionRepository.existsByCandidatureId(1L)).thenReturn(false);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));

        assertThrows(CandidatureNotValideeException.class, () -> admissionService.creerAdmission(1L));
    }
}
