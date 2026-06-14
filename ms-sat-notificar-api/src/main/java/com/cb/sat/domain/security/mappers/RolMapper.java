package com.cb.sat.domain.security.mappers;

import com.cb.sat.domain.security.Rol;
import com.cb.sat.dto.model.rol.RolRequest;
import com.cb.sat.dto.model.rol.RolResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolMapper {

    Rol map(RolRequest request);

    RolResponse map(Rol rol);
}
