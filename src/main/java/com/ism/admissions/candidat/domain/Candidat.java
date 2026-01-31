package com.ism.admissions.candidat.domain;

import com.ism.admissions.common.domain.BaseEntity;
import com.ism.admissions.common.domain.Sexe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(
        name = "candidats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"}),
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidat extends BaseEntity {

        @Column(nullable = false)
        private String nom;

        @Column(nullable = false)
        private String prenom;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Sexe sexe;

        @Column(nullable = false)
        private LocalDate dateNaissance;

        @Column(nullable = false)
        private String nationalite;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String telephone;

//        @Enumerated(EnumType.STRING)
//        @Column(nullable = false)
//        private TypePieceIdentite typePiece;

//        @Column(nullable = false, unique = true)
//        private String numeroPiece;


}
