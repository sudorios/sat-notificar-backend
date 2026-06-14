package com.cb.sat.domain.configuration.mappers;

import com.cb.sat.domain.configuration.Catalogo;
import com.cb.sat.dto.model.catalogo.CatalogoRequest;
import com.cb.sat.dto.model.catalogo.CatalogoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatalogoMapper {

    Catalogo map(CatalogoRequest t);

    CatalogoResponse map(Catalogo t);
}
