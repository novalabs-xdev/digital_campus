package com.ism.admissions.notification.service;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.candidat.domain.Candidat;
import com.ism.admissions.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;
    private final MailService mailService;

    // Méthode utilitaire privée pour éviter la répétition
    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ISM Digital Campus <no-reply@ism.sn>");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Email envoyé avec succès à {}", to);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email à {} : {}", to, e.getMessage());
        }
    }


    @Override
    public void envoyerNotificationAdmission(Admission admission) {
        var candidat = admission.getCandidature().getCandidat();
        var classe = admission.getCandidature().getClasse();

        Map<String, Object> model = new HashMap<>();
        model.put("fullName", candidat.getPrenom() + " " + candidat.getNom());
        model.put("schoolName", classe.getEcole().getNom());
        model.put("admissionNumber", admission.getNumeroAdmission());
        model.put("classeName", classe.getLibelle());
        model.put("portalUrl", "https://votre-portail.com/login"); // À adapter selon ton front

        mailService.sendEmail(
                candidat.getEmail(),
                "Admission Confirmée - " + classe.getEcole().getNom(),
                "admission", // Doit correspondre au nom du fichier HTML
                model
        );
    }


    @Override
    public void envoyerNotificationAccesEtudiant(Admission admission, String password) {
        var candidat = admission.getCandidature().getCandidat();
        var school = admission.getCandidature().getClasse().getEcole();

        Map<String, Object> model = new HashMap<>();
        model.put("fullName", candidat.getPrenom() + " " + candidat.getNom());
        model.put("schoolName", school.getNom());
        model.put("admissionNumber", admission.getNumeroAdmission());
        model.put("email", candidat.getEmail());
        model.put("password", password);
        model.put("loginUrl", "http://localhost:4200/login"); // Ton URL Front

        mailService.sendEmail(
                candidat.getEmail(),
                "Bienvenue à l'ISM - Vos accès Étudiant",
                "admission", // Nom du template HTML (sans .html)
                model
        );
    }

    @Override
    public void envoyerAccesCandidat(Candidat candidat, String password) {
        // Préparation des données pour le template de bienvenue
        Map<String, Object> model = new HashMap<>();
        model.put("fullName", candidat.getPrenom() + " " + candidat.getNom());
        model.put("email", candidat.getEmail());
        model.put("password", password);
        model.put("phone", candidat.getTelephone());
        model.put("country", "Sénégal"); // Ou candidat.getPays() si tu as le champ
        model.put("loginUrl", "http://localhost:4200/login"); // URL de ta page de connexion

        try {
            mailService.sendEmail(
                    candidat.getEmail(),
                    "Bienvenue sur la plateforme d'admission ISM - Vos accès",
                    "registration", // Utilise le template email/registration.html
                    model
            );
            log.info("Email de bienvenue envoyé au candidat : {}", candidat.getEmail());
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email au candidat {} : {}",
                    candidat.getEmail(), e.getMessage());
        }
    }

}
