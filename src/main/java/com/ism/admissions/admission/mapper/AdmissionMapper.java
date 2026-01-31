package com.ism.admissions.admission.mapper;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.dto.AdmissionResponse;
import com.ism.admissions.candidat.mapper.CandidatMapper;
import com.ism.admissions.classe.mapper.ClasseMapper;
import com.ism.admissions.ecole.mapper.EcoleMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {CandidatMapper.class, EcoleMapper.class, ClasseMapper.class},
        builder = @Builder(disableBuilder = true))
public interface AdmissionMapper {

    @Mapping(source = "candidature.candidat", target = "candidat")
    @Mapping(source = "candidature.ecole", target = "ecole")
    @Mapping(source = "candidature.classe", target = "classe")
    AdmissionResponse toResponse(Admission admission);

    List<AdmissionResponse> toResponseList(List<Admission> admissions);
}