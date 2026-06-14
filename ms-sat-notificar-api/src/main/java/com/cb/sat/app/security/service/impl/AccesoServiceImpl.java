package com.cb.sat.app.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cb.sat.app.security.repository.AccesoFiltroRepository;
import com.cb.sat.app.security.repository.AccesoRepository;
import com.cb.sat.app.security.repository.MenuMaestroRepository;
import com.cb.sat.app.security.repository.MenuPerfilRepository;
import com.cb.sat.app.security.repository.MenuRolRepository;
import com.cb.sat.app.security.service.AccesoService;
import com.cb.sat.app.security.service.MenuRolService;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.security.Acceso;
import com.cb.sat.domain.security.MenuMaestro;
import com.cb.sat.domain.security.MenuOpcion;
import com.cb.sat.domain.security.MenuRol;
import com.cb.sat.domain.security.Rol;
import com.cb.sat.domain.security.mappers.MenuMaestroMapper;
import com.cb.sat.domain.security.view.AccesoDataGrid;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.acceso.AccesoMenuItemResponse;
import com.cb.sat.dto.model.acceso.AccesoMenuMaestroRequest;
import com.cb.sat.dto.model.acceso.AccesoMenuResponse;
import com.cb.sat.dto.model.acceso.AccesoOpcionResponse;
import com.cb.sat.dto.model.acceso.AccesoPerfilRequest;
import com.cb.sat.dto.model.acceso.AccesoRolResponse;
import com.cb.sat.dto.model.acceso.MenuConfigResponse;
import com.cb.sat.dto.model.acceso.MenuRequest;
import com.cb.sat.dto.model.acceso.OpcionBotonResponse;
import com.cb.sat.dto.model.acceso.RolMenuConfigResponse;
import com.cb.sat.dto.model.acceso.SubMenuConfigResponse;
import com.cb.sat.dto.model.acceso.UsuarioAccesoMenuRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoResponse;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccesoServiceImpl extends ServiceBase implements AccesoService {

	@Autowired
	private MenuMaestroRepository menuMaestroRepository;

	@Autowired
	private MenuPerfilRepository menuPerfilRepository;

	@Autowired
	private MenuRolRepository menuRolRepository;

	@Autowired
	private MenuRolService menuRolService;

	@Autowired
	private AccesoFiltroRepository accesoFiltroRepository;

	@Autowired
	private AccesoRepository accesoRepository;

	@Autowired
	private MenuMaestroMapper menuMaestroMapper;

	@Override
	public RolMenuConfigResponse getMenu(UUID rolId) {
		RolMenuConfigResponse response = new RolMenuConfigResponse();
		List<MenuConfigResponse> menusConfigResponse = new ArrayList<>();

		List<MenuMaestro> menu = menuMaestroRepository.findByMenuMaestro();
		menu.forEach(tt -> {
			List<SubMenuConfigResponse> subMenuConfig = new ArrayList<>();
			List<MenuMaestro> subMenu = menuMaestroRepository.findByMenuOpcion(tt.getMenuMaestroId());
			MenuConfigResponse menuResponse = menuMaestroMapper.mapMenuConfig(tt);
			menuResponse.setRolId(rolId);
			setMenu(tt, rolId, menuResponse);
			subMenu.forEach(tx -> {
				SubMenuConfigResponse subMenuResponse = menuMaestroMapper.mapSubMenuConfig(tx);
				setSubMenu(tx, rolId, subMenuResponse);
				subMenuConfig.add(subMenuResponse);
			});
			menuResponse.setSubMenus(subMenuConfig);
			menusConfigResponse.add(menuResponse);
		});
		response.setMenusConfig(menusConfigResponse);
		return response;
	}

	private void setMenu(MenuMaestro tt, UUID rolId, MenuConfigResponse menuResponse) {
		Optional<MenuRol> optional = menuPerfilRepository.findByRolAndMenuMaestro(new Rol(rolId),
				new MenuMaestro(tt.getMenuMaestroId()));
		if (optional.isPresent()) {
			menuResponse.setMenuRolId(optional.get().getMenuRolId());
			menuResponse.setHabilitado(optional.get().getHabilitado());
		} else {
			menuResponse.setHabilitado(Constantes.INHABILITADO);
		}
	}

	private void setSubMenu(MenuMaestro tx, UUID rolId, SubMenuConfigResponse subMenuResponse) {
		Optional<MenuRol> optional = menuPerfilRepository.findByRolAndMenuMaestro(new Rol(rolId),
				new MenuMaestro(tx.getMenuMaestroId()));
		if (optional.isPresent()) {
			subMenuResponse.setMenuRolId(optional.get().getMenuRolId());
			subMenuResponse.setHabilitado(optional.get().getHabilitado());
		} else {
			subMenuResponse.setHabilitado(Constantes.INHABILITADO);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdateMenuRol(MenuRequest t) {
		if (GenericUtil.isNotEmpty(t.getMenuRolId())) {
			MenuRol menu = menuRolService.get(t.getMenuRolId());
			menu.setHabilitado(t.getHabilitado());
			menu.setModificado(DateUtil.getCurrentLocalDateTime());
			menu.setModificadoPor(userSesion.getRegistro().getUsuario());
			menuRolRepository.save(menu);
			this.saveOrUpdateSubMenus(t);
		} else {
			MenuRol menu = new MenuRol();
			menu.setMenuMaestro(new MenuMaestro(t.getMenuMaestroId()));
			menu.setRol(new Rol(t.getRolId()));
			menu.setHabilitado(t.getHabilitado());
			menu.setCreadoPor(userSesion.getRegistro().getUsuario());
			menu.setCreado(DateUtil.getCurrentLocalDateTime());
			menuRolRepository.save(menu);
			this.saveOrUpdateSubMenus(t);
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	private void saveOrUpdateSubMenus(MenuRequest t) {
		if (!GenericUtil.isEmpty(t.getSubMenus())) {
			if (Constantes.HABILITADO.equals(t.getHabilitado())) {
				t.getSubMenus().forEach(tt -> {

					if (GenericUtil.isNotEmpty(tt.getMenuRolId())) {
						MenuRol subMenu = menuRolService.get(tt.getMenuRolId());
						subMenu.setModificado(DateUtil.getCurrentLocalDateTime());
						subMenu.setModificadoPor(userSesion.getRegistro().getUsuario());
						subMenu.setHabilitado(tt.getHabilitado());
						menuRolRepository.save(subMenu);
					} else {
						MenuRol subMenu = new MenuRol();
						subMenu.setMenuMaestro(new MenuMaestro(tt.getMenuMaestroId()));
						subMenu.setRol(new Rol(t.getRolId()));
						subMenu.setHabilitado(tt.getHabilitado());
						subMenu.setCreadoPor(userSesion.getRegistro().getUsuario());
						subMenu.setCreado(DateUtil.getCurrentLocalDateTime());
						menuRolRepository.save(subMenu);
					}
				});
			} else {
				t.getSubMenus().forEach(tt -> {
					if (GenericUtil.isNotEmpty(tt.getMenuRolId())) {
						MenuRol subMenu = menuRolService.get(tt.getMenuRolId());
						subMenu.setModificado(DateUtil.getCurrentLocalDateTime());
						subMenu.setModificadoPor(userSesion.getRegistro().getUsuario());
						subMenu.setHabilitado(Constantes.INHABILITADO);
						menuRolRepository.save(subMenu);
					} else {
						MenuRol subMenu = new MenuRol();
						subMenu.setMenuMaestro(new MenuMaestro(tt.getMenuMaestroId()));
						subMenu.setRol(new Rol(t.getRolId()));
						subMenu.setHabilitado(Constantes.INHABILITADO);
						subMenu.setCreadoPor(userSesion.getRegistro().getUsuario());
						subMenu.setCreado(DateUtil.getCurrentLocalDateTime());
						menuRolRepository.save(subMenu);
					}
				});
			}
		}

	}

	@Override
	public UsuarioAccesoResponse get(UUID rolId) {
		UsuarioAccesoResponse usuarioAccesoResponse = new UsuarioAccesoResponse();
		List<AccesoMenuResponse> accesoMenuResponselist = new ArrayList<>();
		List<MenuMaestro> menuMaestrolst = menuMaestroRepository.findByMenuMaestro();
		for (MenuMaestro menuMaestro : menuMaestrolst) {
			AccesoMenuResponse accesoMenuResponse = new AccesoMenuResponse();
			List<MenuMaestro> subMenuMaestroOpcion = menuMaestroRepository
					.findByMenuOpcion(menuMaestro.getMenuMaestroId());
			List<AccesoMenuItemResponse> lstAccesoMenu = new ArrayList<>();
			for (MenuMaestro subMenu : subMenuMaestroOpcion) {
				AccesoMenuItemResponse accesoMenu = new AccesoMenuItemResponse();
				accesoMenu.setMenuMaestroId(subMenu.getMenuMaestroId());
				accesoMenu.setDescripcionMenuMaestro(subMenu.getDescripcion());
				accesoMenu.setNombreMenuMaestro(menuMaestro.getNombre() + " - " + subMenu.getNombre());
				MenuRol menuPerfil = menuPerfilRepository.findByMenuPerfil(rolId, subMenu.getMenuMaestroId());
				accesoMenu
						.setHabilitado(GenericUtil.isNotNull(menuPerfil) ? menuPerfil.getHabilitado() : Constantes.NO);
				if (GenericUtil.isNotNull(menuPerfil)) {
					log.info(menuPerfil.getMenuRolId() + " -> menuPerfil");
				}
				List<OpcionBotonResponse> opcionBotones = new ArrayList<>();
				List<AccesoDataGrid> optional = accesoFiltroRepository.findByMenuPerfil(rolId,
						subMenu.getMenuMaestroId());
				for (AccesoDataGrid botonesConfigurados : optional) {
					OpcionBotonResponse opcionBoton = new OpcionBotonResponse();
					opcionBoton.setBotonId(botonesConfigurados.getMenuOpcionId());
					opcionBoton.setCodigoBoton(botonesConfigurados.getCodigoMenu());
					opcionBoton.setNombreBoton(botonesConfigurados.getNombre());
					opcionBoton.setDescripcion(botonesConfigurados.getDescripcionBoton());
					opcionBoton.setHabilitado(botonesConfigurados.getHabilitado());
					opcionBoton.setMenuMaestroId(botonesConfigurados.getMenuMaestroId());
					opcionBotones.add(opcionBoton);
					accesoMenu.setOpcionBoton(opcionBotones);
				}
				lstAccesoMenu.add(accesoMenu);
			}
			accesoMenuResponse.setAccesoMenu(lstAccesoMenu);
			accesoMenuResponselist.add(accesoMenuResponse);
		}
		usuarioAccesoResponse.setAccesoMenuResponse(null);
		usuarioAccesoResponse.setAccesoMenuResponse(accesoMenuResponselist);
		return usuarioAccesoResponse;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdateAccesoBtns(UsuarioAccesoMenuRequest t) {
		List<Acceso> accesoLst = new ArrayList<>();
		List<AccesoPerfilRequest> accesoPerfilRequest = t.getAccesoPerfil();
		UUID perfilId = t.getRolId();

		if (GenericUtil.isNotNull(t.getMenuMaestroId())) {
			UUID menuMaestroId = t.getMenuMaestroId();
			this.updateMenuPerfil(perfilId, menuMaestroId, t.getHabilitado());
		}

		for (AccesoPerfilRequest accesoRequest : accesoPerfilRequest) {
			deleteAccesoxMenu(accesoRequest.getMenuOpcionId(), perfilId);
			Acceso acceso = new Acceso();
			acceso.setHabilitado(accesoRequest.getHabilitado());
			Rol perfil = new Rol();
			perfil.setRolId(perfilId);
			acceso.setRol(perfil);
			MenuOpcion menuOpcion = new MenuOpcion();
			menuOpcion.setMenuOpcionId(accesoRequest.getMenuOpcionId());
			acceso.setMenuOpcion(menuOpcion);
			acceso.setCreadoPor(userSesion.getRegistro().getUsuario());
			acceso.setCreado(DateUtil.getCurrentLocalDateTime());
			acceso.setModificado(DateUtil.getCurrentLocalDateTime());
			acceso.setModificadoPor(userSesion.getRegistro().getUsuario());
			accesoLst.add(acceso);
		}
		accesoRepository.saveAll(accesoLst);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void deleteAccesoxMenu(UUID menuOpcionId, UUID perfilId) {
		accesoRepository.deleteAccesoxMenu(menuOpcionId, perfilId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	private void updateMenuPerfil(UUID perfilId, UUID menuMaestroId, Boolean habilitado) {

		MenuRol menuPerfil = menuRolRepository.findByMenuPerfil(perfilId, menuMaestroId);
		if (GenericUtil.isNotNull(menuPerfil) || GenericUtil.isNotEmpty(menuPerfil)) {
			menuPerfil.setHabilitado(habilitado);
			menuPerfil.setModificado(DateUtil.getCurrentLocalDateTime());
			menuPerfil.setModificadoPor(userSesion.getRegistro().getUsuario());
			menuRolRepository.save(menuPerfil);

		} else {
			MenuRol menuPerfilNuevo = new MenuRol();
			Rol perfil = new Rol();
			perfil.setRolId(perfilId);

			MenuMaestro menuMaestro = new MenuMaestro();
			menuMaestro.setMenuMaestroId(menuMaestroId);

			menuPerfilNuevo.setRol(perfil);
			menuPerfilNuevo.setMenuMaestro(menuMaestro);
			menuPerfilNuevo.setHabilitado(habilitado);
			menuPerfilNuevo.setCreadoPor(userSesion.getRegistro().getUsuario());
			menuPerfilNuevo.setCreado(DateUtil.getCurrentLocalDateTime());
			menuPerfilNuevo.setModificado(DateUtil.getCurrentLocalDateTime());
			menuPerfilNuevo.setModificadoPor(userSesion.getRegistro().getUsuario());
			menuRolRepository.save(menuPerfilNuevo);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdate(UsuarioAccesoRequest t) {
		List<Acceso> accesoLst = new ArrayList<>();
		List<AccesoPerfilRequest> accesoPerfilRequest = t.getAccesoPerfil();
		UUID perfilId = t.getRolId();
		this.delete(perfilId);

		if (GenericUtil.isNotNull(t.getMenuMaestroRequest())) {
			List<AccesoMenuMaestroRequest> menuPerfilRequest = t.getMenuMaestroRequest();
			for (AccesoMenuMaestroRequest menuMaestro : menuPerfilRequest) {
				this.updateMenuPerfil(perfilId, menuMaestro.getMenuMaestroId(), menuMaestro.getHabilitado());
			}
		}

		for (AccesoPerfilRequest accesoRequest : accesoPerfilRequest) {
			Acceso acceso = new Acceso();
			acceso.setHabilitado(accesoRequest.getHabilitado());
			Rol perfil = new Rol();
			perfil.setRolId(perfilId);
			acceso.setRol(perfil);
			MenuOpcion menuOpcion = new MenuOpcion();
			menuOpcion.setMenuOpcionId(accesoRequest.getMenuOpcionId());
			acceso.setMenuOpcion(menuOpcion);
			acceso.setCreadoPor(userSesion.getRegistro().getUsuario());
			acceso.setCreado(DateUtil.getCurrentLocalDateTime());
			acceso.setModificado(DateUtil.getCurrentLocalDateTime());
			acceso.setModificadoPor(userSesion.getRegistro().getUsuario());
			accesoLst.add(acceso);
		}
		accesoRepository.saveAll(accesoLst);

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	private void delete(UUID perfilId) {
		accesoRepository.deleteAcceso(perfilId);
	}

	@Override
	public AccesoRolResponse getAcceso(UUID menuId, String perfilId) {
		List<AccesoOpcionResponse> collection = new ArrayList<>();
		List<AccesoDataGrid> accesoGrid = accesoRepository.getAccesoMenu(menuId, perfilId);
		AccesoRolResponse accesoResponse = new AccesoRolResponse();
		accesoResponse.setOpciones(collection);
		accesoGrid.forEach(tt -> {
			collection.add(new AccesoOpcionResponse(tt.getNombre(), tt.getCodigoMenu(), tt.getHabilitado()));
		});
		return accesoResponse;
	}
}