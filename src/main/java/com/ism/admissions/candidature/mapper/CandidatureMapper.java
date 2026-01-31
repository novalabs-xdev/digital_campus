package com.ism.admissions.candidature.mapper;

import com.ism.admissions.candidat.mapper.CandidatMapper;
import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.DocumentCandidature;
import com.ism.admissions.candidature.dto.CandidatureDetailResponse;
import com.ism.admissions.candidature.dto.CandidatureResponse;
import com.ism.admissions.candidature.dto.DocumentResponse;
import com.ism.admissions.classe.mapper.ClasseMapper;
import com.ism.admissions.ecole.mapper.EcoleMapper;
import com.ism.admissions.user.domain.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {CandidatMapper.class, EcoleMapper.class, ClasseMapper.class},
        builder = @Builder(disableBuilder = true))
public interface CandidatureMapper {

    @Mapping(source = "creePar", target = "creePar", qualifiedByName = "userToEmail")
    CandidatureResponse toResponse(Candidature candidature);

    @Mapping(source = "creePar", target = "creePar", qualifiedByName = "userToEmail")
    @Mapping(source = "documents", target = "documents")
    CandidatureDetailResponse toDetailResponse(Candidature candidature);

    DocumentResponse toDocumentResponse(DocumentCandidature document);

    List<CandidatureResponse> toResponseList(List<Candidature> candidatures);

    @Named("userToEmail")
    default String userToEmail(User user) {
        return user != null ? user.getEmail() : null;
    }
}