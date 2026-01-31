package com.ism.admissions.candidat.service;

import com.ism.admissions.candidat.domain.Candidat;
import com.ism.admissions.candidat.exception.CandidatDoublonException;
import com.ism.admissions.candidat.repository.CandidatRepository;
import com.ism.admissions.common.util.PasswordGenerator;
import com.ism.admissions.notification.service.NotificationService;
import com.ism.admissions.user.domain.Role;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidatServiceImpl implements CandidatService {
    private final CandidatRepository candidatRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Candidat creerCandidat(Candidat candidat) {
        if (candidatRepository.existsByEmail(candidat.getEmail())) {
            throw new CandidatDoublonException("Candidat avec cet email existe déjà");
        }
        return candidatRepository.save(candidat);
    }

    @Override
    @Transactional
    public Candidat creerCandidatAvecCompte(Candidat candidat) {
        // 1. Sauvegarder le candidat
        Candidat nouveauCandidat = creerCandidat(candidat);

        // 2. Créer l'utilisateur associé
        String rawPassword = PasswordGenerator.generate(8);

        User user = new User();
        user.setEmail(nouveauCandidat.getEmail());
        user.setPrenom(nouveauCandidat.getPrenom());
        user.setNom(nouveauCandidat.getNom());
        user.setRole(Role.CANDIDAT); // Utilise ton enum mis à jour
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);

        userRepository.save(user);

        // 3. Envoyer le mail de bienvenue avec les accès
        // On réutilise ton template de registration adapté
        notificationService.envoyerAccesCandidat(nouveauCandidat, rawPassword);

        log.info("Compte Candidat créé pour : {}", nouveauCandidat.getEmail());
        return nouveauCandidat;
    }

    @Override
    public Optional<Candidat> rechercherParEmail(String email) {
        return candidatRepository.findByEmail(email);
    }
}
