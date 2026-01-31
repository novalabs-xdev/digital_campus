package com.ism.admissions.historique_candidature.mapper;

import com.ism.admissions.historique_candidature.domain.HistoriqueStatutCandidature;
import com.ism.admissions.historique_candidature.dto.HistoriqueStatutResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoriqueCandidatureMapper {
    HistoriqueStatutResponse toResponse(HistoriqueStatutCandidature historique);

    List<HistoriqueStatutResponse> toResponseList(
            List<HistoriqueStatutCandidature> historiques
    );
}
