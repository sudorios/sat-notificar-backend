package com.cb.sat.domain.security.mappers;

import com.cb.sat.domain.security.MenuMaestro;
import com.cb.sat.dto.model.acceso.MenuConfigResponse;
import com.cb.sat.dto.model.acceso.SubMenuConfigResponse;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroItemResponse;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroRequest;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMaestroMapper {

    MenuMaestro map(MenuMaestroRequest request);

    MenuConfigResponse mapMenuConfig(MenuMaestro maestro);

    MenuMaestroResponse map(MenuMaestro maestro);

    MenuMaestroItemResponse mapMenuItem(MenuMaestro maestro);

    SubMenuConfigResponse mapSubMenuConfig(MenuMaestro maestro);
}
