package com.ism.admissions.candidature.service;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.service.AdmissionService;
import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.candidature.exception.CandidatureNotFoundException;
import com.ism.admissions.candidature.exception.CandidatureStatutInvalideException;
import com.ism.admissions.candidature.exception.DocumentsManquantsException;
import com.ism.admissions.candidature.repository.CandidatureRepository;
import com.ism.admissions.exception.business.UserNotFoundException;
import com.ism.admissions.historique_candidature.service.HistoriqueCandidatureService;
import com.ism.admissions.notification.service.NotificationService;
import com.ism.admissions.security.current.CurrentUserProvider;
import com.ism.admissions.user.domain.Role;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.repository.UserRepository;
import com.ism.admissions.common.util.PasswordGenerator; // Assure-toi de créer cette classe utilitaire
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidatureServiceImpl implements CandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final UserRepository userRepository;
    private final AdmissionService admissionService;
    private final NotificationService notificationService;
    private final PasswordEncoder passwordEncoder;
    private final HistoriqueCandidatureService historiqueCandidatureService;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional
    public void soumettreCandidature(Long candidatureId) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new CandidatureNotFoundException(candidatureId));

        if (candidature.getStatut() != StatutCandidature.BROUILLON) {
            throw new CandidatureStatutInvalideException(candidature.getStatut(), "soumettre");
        }

        if (candidature.getDocuments() == null || candidature.getDocuments().isEmpty()) {
            throw new DocumentsManquantsException();
        }

        StatutCandidature ancienStatut = candidature.getStatut();
        candidature.setStatut(StatutCandidature.SOUMISE);
        candidatureRepository.save(candidature);

        historiqueCandidatureService.enregistrerTransition(
                candidature, ancienStatut, StatutCandidature.SOUMISE,
                "SOUMISSION", currentUserProvider.getCurrentUser().getEmail());

        log.info("Candidature {} soumise avec succès", candidatureId);
    }

    @Override
    @Transactional
    public void preValiderParAgent(Long candidatureId) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new CandidatureNotFoundException(candidatureId));

        if (candidature.getStatut() != StatutCandidature.SOUMISE) {
            throw new CandidatureStatutInvalideException(candidature.getStatut(), "pré-valider");
        }

        StatutCandidature ancienStatut = candidature.getStatut();
        candidature.setStatut(StatutCandidature.A_VALIDER);
        candidatureRepository.save(candidature);

        historiqueCandidatureService.enregistrerTransition(
                candidature, ancienStatut, StatutCandidature.A_VALIDER,
                "PRE_VALIDATION", currentUserProvider.getCurrentUser().getEmail());

        log.info("Candidature {} pré-validée par l'agent", candidatureId);
    }

    @Override
    @Transactional
    public void validerParDirecteur(Long candidatureId) {
        // 1. Récupération et vérification
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new CandidatureNotFoundException(candidatureId));

        if (candidature.getStatut() != StatutCandidature.A_VALIDER) {
            throw new CandidatureStatutInvalideException(candidature.getStatut(), "valider");
        }

        // 2. Mise à jour du statut
        StatutCandidature ancienStatut = candidature.getStatut();
        candidature.setStatut(StatutCandidature.VALIDEE);
        candidatureRepository.save(candidature);

        historiqueCandidatureService.enregistrerTransition(
                candidature, ancienStatut, StatutCandidature.VALIDEE,
                "VALIDATION", currentUserProvider.getCurrentUser().getEmail());

        // 3. Création de l'admission (Génère le matricule AD-2026-...)
        Admission admission = admissionService.creerAdmission(candidature.getId());

        // 4. Mutation du compte : CANDIDAT -> ETUDIANT
        User user = userRepository.findByEmail(candidature.getCandidat().getEmail())
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable pour l'email : " + candidature.getCandidat().getEmail()));

        String passwordEtudiant = PasswordGenerator.generate(10);
        user.setRole(Role.ETUDIANT);
        user.setPassword(passwordEncoder.encode(passwordEtudiant));
        userRepository.save(user);

        // 5. Notification finale avec nouveaux accès (Template ISM Digital Campus)
        notificationService.envoyerNotificationAccesEtudiant(admission, passwordEtudiant);

        log.info("Candidature {} validée. Compte migré en ETUDIANT avec le matricule {}",
                candidatureId, admission.getNumeroAdmission());
    }

    @Override
    @Transactional
    public void rejeterParDirecteur(Long candidatureId, String motif) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new CandidatureNotFoundException(candidatureId));

        if (candidature.getStatut() != StatutCandidature.A_VALIDER) {
            throw new CandidatureStatutInvalideException(candidature.getStatut(), "rejeter");
        }

        StatutCandidature ancienStatut = candidature.getStatut();
        candidature.setStatut(StatutCandidature.REJETEE);
        candidatureRepository.save(candidature);

        historiqueCandidatureService.enregistrerTransition(
                candidature, ancienStatut, StatutCandidature.REJETEE,
                "REJET", currentUserProvider.getCurrentUser().getEmail());

        log.warn("Candidature {} rejetée par le directeur. Motif : {}", candidatureId, motif);
    }
}