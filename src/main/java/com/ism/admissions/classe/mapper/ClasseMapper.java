package com.ism.admissions.classe.mapper;

import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.classe.dto.ClasseCreateRequest;
import com.ism.admissions.classe.dto.ClasseResponse;
import com.ism.admissions.ecole.mapper.EcoleMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {EcoleMapper.class},
        builder = @Builder(disableBuilder = true))
public interface ClasseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ecole", ignore = true) // Sera défini dans le service
    @Mapping(target = "actif", constant = "true")
    Classe toEntity(ClasseCreateRequest request);

    ClasseResponse toResponse(Classe classe);

    List<ClasseResponse> toResponseList(List<Classe> classes);
}
