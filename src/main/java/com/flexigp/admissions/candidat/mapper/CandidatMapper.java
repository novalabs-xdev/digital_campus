package com.flexigp.admissions.candidat.mapper;

import com.flexigp.admissions.candidat.domain.Candidat;
import com.flexigp.admissions.candidat.dto.CandidatCreateRequest;
import com.flexigp.admissions.candidat.dto.CandidatResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public interface CandidatMapper {
    /* DTO -> Domain */
    @Mapping(target = "id", ignore = true)
    Candidat toEntity(CandidatCreateRequest request);

    /* Domain -> DTO */
    CandidatResponse toResponse(Candidat candidat);
}
