package com.cb.sat.app.security.facade;

import java.util.UUID;

import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.acceso.AccesoRolResponse;
import com.cb.sat.dto.model.acceso.MenuConfigRequest;
import com.cb.sat.dto.model.acceso.RolMenuConfigResponse;
import com.cb.sat.dto.model.acceso.UsuarioAccesoMenuRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoResponse;


public interface AccesoFacade {

	RolMenuConfigResponse getMenu(UUID rolId);

	BaseOperacionResponse saveOrUpdateMenuRol(MenuConfigRequest request);

	UsuarioAccesoResponse get(UUID rolId);

	BaseOperacionResponse saveOrUpdateAccesoBtns(UsuarioAccesoMenuRequest request);

	BaseOperacionResponse saveOrUpdate(UsuarioAccesoRequest request);

	AccesoRolResponse check(UUID menuId);
}
