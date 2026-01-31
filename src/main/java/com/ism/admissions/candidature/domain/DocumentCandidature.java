package com.ism.admissions.candidature.domain;

import com.ism.admissions.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "documents_candidature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentCandidature extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "candidature_id")
    private Candidature candidature;

    /**
     * Type fonctionnel du document (BAC, RELEVE_NOTE, etc.)
     */
    @Column(nullable = false)
    private String typeDocument;

    /**
     * Nom original du fichier
     */
    @Column(nullable = false)
    private String nomOriginal;

    /**
     * Identifiant S3 (clé ou URL)
     */
    @Column(nullable = false)
    private String storageKey;

    /**
     * Date d’upload
     */
    @Column(nullable = false)
    private LocalDateTime dateUpload;
}
