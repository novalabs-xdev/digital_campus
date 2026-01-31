package com.ism.admissions.ecole.mapper;

import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.ecole.dto.EcoleCreateRequest;
import com.ism.admissions.ecole.dto.EcoleResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface EcoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "actif", constant = "true")
    Ecole toEntity(EcoleCreateRequest request);

    EcoleResponse toResponse(Ecole ecole);

    List<EcoleResponse> toResponseList(List<Ecole> ecoles);
}