package com.flexigp.admissions.admission;

import com.flexigp.admissions.candidature.domain.Candidature;
import com.flexigp.admissions.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "admissions",
        uniqueConstraints = @UniqueConstraint(columnNames = "candidature_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admission extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "candidature_id", nullable = false)
    private Candidature candidature;

    @Column(nullable = false)
    private LocalDateTime dateAdmission;

    /**
     * Numéro/matricule d’admission (optionnel, généré plus tard)
     */
    @Column(unique = true)
    private String numeroAdmission;

}
