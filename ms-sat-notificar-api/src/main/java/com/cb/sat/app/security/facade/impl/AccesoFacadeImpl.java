package com.cb.sat.app.security.facade.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cb.sat.app.security.facade.AccesoFacade;
import com.cb.sat.app.security.service.AccesoService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.acceso.AccesoPerfilRequest;
import com.cb.sat.dto.model.acceso.AccesoRolResponse;
import com.cb.sat.dto.model.acceso.MenuConfigRequest;
import com.cb.sat.dto.model.acceso.RolMenuConfigResponse;
import com.cb.sat.dto.model.acceso.UsuarioAccesoMenuRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoResponse;
import com.cb.sat.dto.util.GenericUtil;

@Component
public class AccesoFacadeImpl extends FacadeBase implements AccesoFacade {

	@Autowired
	private AccesoService accesoService;

	@Override
	public RolMenuConfigResponse getMenu(UUID rolId) {
		return accesoService.getMenu(rolId);
	}

	@Override
	public BaseOperacionResponse saveOrUpdateMenuRol(MenuConfigRequest t) {
		if (GenericUtil.isNull(t)) {
			throw new InternalException("Lista null");
		}
		t.getMenusConfig().forEach(tt -> {
			accesoService.saveOrUpdateMenuRol(tt);
		});
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public UsuarioAccesoResponse get(UUID rolId) {
		return accesoService.get(rolId);
	}

	@Override
	public BaseOperacionResponse saveOrUpdateAccesoBtns(UsuarioAccesoMenuRequest t) {
		if (GenericUtil.isNull(t.getRolId())) {
			throw new InternalException("El código del Perfil es Obligatorio");
		}
		if (GenericUtil.isNull(t.getMenuMaestroId())) {
			throw new InternalException("El código del Menu Maestro es Obligatorio");
		}
		for (AccesoPerfilRequest data : t.getAccesoPerfil()) {
			if (GenericUtil.isNull(data.getMenuOpcionId())) {
				throw new InternalException("El código del Menu es Obligatorio");
			}
		}
		accesoService.saveOrUpdateAccesoBtns(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public BaseOperacionResponse saveOrUpdate(UsuarioAccesoRequest t) {
		if (GenericUtil.isNull(t.getRolId())) {
			throw new InternalException("El código del Perfil es Obligatorio");
		}

		for (AccesoPerfilRequest data : t.getAccesoPerfil()) {
			if (GenericUtil.isNull(data.getMenuOpcionId())) {
				throw new InternalException("El código del Menu es Obligatorio");
			}
		}
		accesoService.saveOrUpdate(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public AccesoRolResponse check(UUID menuId) {
		return accesoService.getAcceso(menuId, userSesion.getRegistro().getRolCodigo());
	}
}
