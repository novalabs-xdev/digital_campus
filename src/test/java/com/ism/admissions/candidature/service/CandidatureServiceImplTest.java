package com.ism.admissions.candidature.service;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.service.AdmissionService;
import com.ism.admissions.candidat.domain.Candidat;
import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.DocumentCandidature;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.candidature.exception.CandidatureNotFoundException;
import com.ism.admissions.candidature.exception.CandidatureStatutInvalideException;
import com.ism.admissions.candidature.exception.DocumentsManquantsException;
import com.ism.admissions.candidature.repository.CandidatureRepository;
import com.ism.admissions.historique_candidature.service.HistoriqueCandidatureService;
import com.ism.admissions.notification.service.NotificationService;
import com.ism.admissions.security.current.CurrentUserProvider;
import com.ism.admissions.user.domain.Role;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidatureServiceImplTest {

    @Mock private CandidatureRepository candidatureRepository;
    @Mock private UserRepository userRepository;
    @Mock private AdmissionService admissionService;
    @Mock private NotificationService notificationService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private HistoriqueCandidatureService historiqueCandidatureService;
    @Mock private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private CandidatureServiceImpl candidatureService;

    private User currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new User();
        currentUser.setEmail("agent@ism.sn");
        lenient().when(currentUserProvider.getCurrentUser()).thenReturn(currentUser);
    }

    // --- soumettreCandidature ---

    @Test
    void soumettreCandidature_success() {
        Candidature candidature = buildCandidature(1L, StatutCandidature.BROUILLON);
        candidature.setDocuments(List.of(new DocumentCandidature()));

        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        when(candidatureRepository.save(any())).thenReturn(candidature);

        candidatureService.soumettreCandidature(1L);

        assertEquals(StatutCandidature.SOUMISE, candidature.getStatut());
        verify(candidatureRepository).save(candidature);
        verify(historiqueCandidatureService).enregistrerTransition(
                eq(candidature), eq(StatutCandidature.BROUILLON), eq(StatutCandidature.SOUMISE),
                eq("SOUMISSION"), eq("agent@ism.sn"));
    }

    @Test
    void soumettreCandidature_notFound_throws() {
        when(candidatureRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CandidatureNotFoundException.class, () -> candidatureService.soumettreCandidature(99L));
    }

    @Test
    void soumettreCandidature_wrongStatus_throws() {
        Candidature candidature = buildCandidature(1L, StatutCandidature.SOUMISE);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        assertThrows(CandidatureStatutInvalideException.class, () -> candidatureService.soumettreCandidature(1L));
    }

    @Test
    void soumettreCandidature_noDocuments_throws() {
        Candidature candidature = buildCandidature(1L, StatutCandidature.BROUILLON);
        candidature.setDocuments(new ArrayList<>());
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        assertThrows(DocumentsManquantsException.class, () -> candidatureService.soumettreCandidature(1L));
    }

    // --- preValiderParAgent ---

    @Test
    void preValiderParAgent_success() {
        Candidature candidature = buildCandidature(1L, StatutCandidature.SOUMISE);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        when(candidatureRepository.save(any())).thenReturn(candidature);

        candidatureService.preValiderParAgent(1L);

        assertEquals(StatutCandidature.A_VALIDER, candidature.getStatut());
        verify(historiqueCandidatureService).enregistrerTransition(
                eq(candidature), eq(StatutCandidature.SOUMISE), eq(StatutCandidature.A_VALIDER),
                eq("PRE_VALIDATION"), eq("agent@ism.sn"));
    }

    @Test
    void preValiderParAgent_wrongStatus_throws() {
        Candidature candidature = buildCandidature(1L, StatutCandidature.BROUILLON);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        assertThrows(CandidatureStatutInvalideException.class, () -> candidatureService.preValiderParAgent(1L));
    }

    // --- validerParDirecteur ---

    @Test
    void validerParDirecteur_success() {
        Candidat candidat = Candidat.builder().email("etudiant@test.com").build();
        Candidature candidature = buildCandidature(1L, StatutCandidature.A_VALIDER);
        candidature.setCandidat(candidat);

        Admission admission = Admission.builder().numeroAdmission("AD-2026-ISM-000001").build();

        User user = new User();
        user.setRole(Role.CANDIDAT);

        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        when(candidatureRepository.save(any())).thenReturn(candidature);
        when(admissionService.creerAdmission(1L)).thenReturn(admission);
        when(userRepository.findByEmail("etudiant@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        candidatureService.validerParDirecteur(1L);

        assertEquals(StatutCandidature.VALIDEE, candidature.getStatut());
        assertEquals(Role.ETUDIANT, user.getRole());
        verify(notificationService).envoyerNotificationAccesEtudiant(eq(admission), anyString());
        verify(historiqueCandidatureService).enregistrerTransition(
                eq(candidature), eq(StatutCandidature.A_VALIDER), eq(StatutCandidature.VALIDEE),
                eq("VALIDATION"), eq("agent@ism.sn"));
    }

    // --- rejeterParDirecteur ---

    @Test
    void rejeterParDirecteur_success() {
        Candidature candidature = buildCandidature(1L, StatutCandidature.A_VALIDER);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        when(candidatureRepository.save(any())).thenReturn(candidature);

        candidatureService.rejeterParDirecteur(1L, "Dossier incomplet");

        assertEquals(StatutCandidature.REJETEE, candidature.getStatut());
        verify(historiqueCandidatureService).enregistrerTransition(
                eq(candidature), eq(StatutCandidature.A_VALIDER), eq(StatutCandidature.REJETEE),
                eq("REJET"), eq("agent@ism.sn"));
    }

    @Test
    void rejeterParDirecteur_wrongStatus_throws() {
        Candidature candidature = buildCandidature(1L, StatutCandidature.SOUMISE);
        when(candidatureRepository.findById(1L)).thenReturn(Optional.of(candidature));
        assertThrows(CandidatureStatutInvalideException.class,
                () -> candidatureService.rejeterParDirecteur(1L, "motif"));
    }

    // --- Helper ---

    private Candidature buildCandidature(Long id, StatutCandidature statut) {
        Candidature c = new Candidature();
        c.setId(id);
        c.setStatut(statut);
        return c;
    }
}
