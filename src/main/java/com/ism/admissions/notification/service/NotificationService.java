package com.ism.admissions.notification.service;


import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.candidat.domain.Candidat;

public interface NotificationService {
    void envoyerNotificationAdmission(Admission admission);

    void envoyerNotificationAccesEtudiant(Admission admission, String password);

    void envoyerAccesCandidat(Candidat nouveauCandidat, String rawPassword);
}
