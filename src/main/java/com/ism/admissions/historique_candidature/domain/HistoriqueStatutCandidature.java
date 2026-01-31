package com.ism.admissions.historique_candidature.domain;

import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_statut_candidature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueStatutCandidature extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidature_id")
    private Candidature candidature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCandidature ancienStatut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCandidature nouveauStatut;

    @Column(nullable = false)
    private String action; // SOUMISSION, PRE_VALIDATION, VALIDATION, REJET

    @Column(nullable = false)
    private LocalDateTime dateAction;


    @Column(nullable = false)
    private String effectuePar;
}

