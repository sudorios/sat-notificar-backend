package com.cb.sat.domain.security.mappers;

import com.cb.sat.domain.security.MenuOpcion;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionRequest;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuOpcionMapper {

    MenuOpcion map(MenuOpcionRequest request);

    MenuOpcionResponse map(MenuOpcion opcion);
}
