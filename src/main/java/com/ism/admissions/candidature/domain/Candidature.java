package com.ism.admissions.candidature.domain;

import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.candidat.domain.Candidat;
import com.ism.admissions.common.domain.BaseEntity;
import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.historique_candidature.domain.HistoriqueStatutCandidature;
import com.ism.admissions.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidature extends BaseEntity {

    @ManyToOne(optional = false)
    private Candidat candidat;

    @ManyToOne(optional = false)
    private Ecole ecole;

    @ManyToOne(optional = false)
    private Classe classe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCandidature statut;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @ManyToOne(optional = false)
    private User creePar;

    @OneToMany(
            mappedBy = "candidature",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DocumentCandidature> documents = new ArrayList<>();

    @OneToMany(
            mappedBy = "candidature",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<HistoriqueStatutCandidature> historiqueStatuts = new ArrayList<>();
}
