package com.cb.sat.app.security.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cb.sat.app.configuration.facade.ComunFacade;
import com.cb.sat.app.security.facade.UsuarioFacade;
import com.cb.sat.app.security.service.MenuRolService;
import com.cb.sat.app.security.service.UsuarioService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.domain.security.Usuario;
import com.cb.sat.domain.security.mappers.UsuarioMapper;
import com.cb.sat.domain.security.view.MenuRolDataGrid;
import com.cb.sat.domain.security.view.UsuarioDataGrid;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.auth.menu.MenuRolResponse;
import com.cb.sat.dto.model.usuario.UsuarioCambioPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioComboResponse;
import com.cb.sat.dto.model.usuario.UsuarioDataGridResponse;
import com.cb.sat.dto.model.usuario.UsuarioFiltroRequest;
import com.cb.sat.dto.model.usuario.UsuarioFindResponse;
import com.cb.sat.dto.model.usuario.UsuarioRequest;
import com.cb.sat.dto.model.usuario.UsuarioResetPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioResponse;
import com.cb.sat.dto.util.GenericUtil;

@Component
public class UsuarioFacadeImpl extends FacadeBase implements UsuarioFacade {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ComunFacade comunFacade;

	@Autowired
	private MenuRolService menuRolService;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@Override
	public UsuarioComboResponse init() {
		UsuarioComboResponse combo = new UsuarioComboResponse();
		combo.setEstado(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.ESTADO_USUARIO));
		combo.setTipoDocumento(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.TIPO_DOCUMENTO));
		combo.setRol(comunFacade.loadRol());
		combo.setEmpresa(comunFacade.loadEmpresa());
		return combo;
	}

	@Override
	public UsuarioResponse initForm() {
		UsuarioResponse response = new UsuarioResponse();
		UsuarioComboResponse combo = new UsuarioComboResponse();
		combo.setEstado(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.ESTADO_USUARIO));
		combo.setTipoDocumento(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.TIPO_DOCUMENTO));
		combo.setRol(comunFacade.loadRol());
		combo.setEmpresa(comunFacade.loadEmpresa());
		response.setCombo(combo);
		return response;
	}

	@Override
	public CollectionResponse<UsuarioFindResponse> find(UsuarioFiltroRequest t) {
		List<UsuarioFindResponse> collection = new ArrayList<>();
		List<UsuarioDataGrid> listDto = usuarioService.find(t);
		listDto.forEach(tt -> {
			collection.add(usuarioMapper.map(tt));
		});
		return new CollectionResponse<>(collection, t.getStart(), t.getLimit(), t.getTotalCount());
	}

	@Override
	public BaseOperacionResponse saveOrUpdate(UsuarioRequest t) {
		GenericUtil.toUpperCase(t);
		usuarioService.saveOrUpdate(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public MenuRolResponse loadMenu() {
		MenuRolResponse menuResponse = new MenuRolResponse();
		List<MenuRolDataGrid> menuRols = menuRolService.loadMenuByRolId(userSesion.getRegistro().getRolCodigo());
		List<Map<String, Object>> children = new ArrayList<>();
		for (MenuRolDataGrid menu : menuRols) {
			children.add(getMapMenuRol(menu));
		}
		menuResponse.setMenu(children);
		return menuResponse;
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Object> getMapMenuRol(MenuRolDataGrid menu) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("menuRolId", menu.getMenuRolId());
		response.put("text", menu.getDescripcion());
		response.put("title", menu.getNombre());
		response.put("to", menu.getUrl());
		response.put("icono", menu.getIcono());
		List<MenuRolDataGrid> list = menuRolService.loadMenuHijo(menu.getRolCodigo(), menu.getMenuMaestroId());
		if (!list.isEmpty()) {
			Iterator iterator = list.iterator();
			List<Object> children = new ArrayList<Object>();
			while (iterator.hasNext()) {
				MenuRolDataGrid cat = (MenuRolDataGrid) iterator.next();
				children.add(getMapMenuRol(cat));
			}
			response.put("children", children);
		}
		return response;
	}

	@Override
	public BaseOperacionResponse updatePassword(UsuarioCambioPasswordRequest t) {
		this.validarPassword(t.getNuevoPassword(), t.getConfirmarPassword());
		usuarioService.updatePassword(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageUpdatePassword);
	}

	private void validarPassword(String nuevoPassword, String confirmarPassword) {
		if (GenericUtil.isNull(nuevoPassword) || GenericUtil.isNull(confirmarPassword)
				|| !nuevoPassword.equals(confirmarPassword)) {
			throw new InternalException("Las contraseñas no coinciden. Verifique e intente nuevamente.");
		}
	}

	@Override
	public BaseOperacionResponse resetPassword(UsuarioResetPasswordRequest t) {
		usuarioService.resetPasword(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageResetPassword);
	}

	@Override
	public UsuarioResponse get(UUID usuarioId) {
		Usuario u = usuarioService.get(usuarioId);
		UsuarioResponse response = usuarioMapper.map(u);
		if (GenericUtil.isNotNull(u.getRol())) {
			response.setRolId(u.getRol().getRolId());
		}
		UsuarioComboResponse combo = new UsuarioComboResponse();
		combo.setEmpresa(comunFacade.loadEmpresa());
		combo.setTipoDocumento(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.TIPO_DOCUMENTO));
		combo.setEstado(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.ESTADO_USUARIO));
		combo.setRol(comunFacade.loadRol());
		response.setEmpresaId(u.getEmpresa().getEmpresaId());
		response.setCombo(combo);
		return response;
	}

	@Override
	public UsuarioDataGridResponse getById(UUID usuarioId) {
		return usuarioMapper.mapUsuario(usuarioService.getById(usuarioId));
	}

}
