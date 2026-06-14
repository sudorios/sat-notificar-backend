package com.cb.sat.app.security.service;

import java.util.UUID;

import com.cb.sat.dto.model.acceso.AccesoRolResponse;
import com.cb.sat.dto.model.acceso.MenuRequest;
import com.cb.sat.dto.model.acceso.RolMenuConfigResponse;
import com.cb.sat.dto.model.acceso.UsuarioAccesoMenuRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoResponse;

public interface AccesoService {

	RolMenuConfigResponse getMenu(UUID rolId);

	void saveOrUpdateMenuRol(MenuRequest tt);

	UsuarioAccesoResponse get(UUID rolId);

	void saveOrUpdateAccesoBtns(UsuarioAccesoMenuRequest t);

	void saveOrUpdate(UsuarioAccesoRequest t);
	
	 AccesoRolResponse getAcceso(UUID menuId, String perfilId);
}
