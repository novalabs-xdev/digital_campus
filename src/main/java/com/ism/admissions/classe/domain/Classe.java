package com.ism.admissions.classe.domain;

import com.ism.admissions.common.domain.BaseEntity;
import com.ism.admissions.ecole.domain.Ecole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Classe extends BaseEntity {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @ManyToOne(optional = false)
    private Ecole ecole;

    private Integer capacite;

    private boolean actif = true;
}
