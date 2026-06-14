package com.cb.sat.app.security.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cb.sat.app.security.facade.MenuFacade;
import com.cb.sat.app.security.service.MenuMaestroService;
import com.cb.sat.app.security.service.MenuOpcionService;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.domain.security.MenuMaestro;
import com.cb.sat.domain.security.MenuOpcion;
import com.cb.sat.domain.security.mappers.MenuMaestroMapper;
import com.cb.sat.domain.security.mappers.MenuOpcionMapper;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroItemResponse;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroRequest;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroResponse;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionRequest;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionResponse;
import com.cb.sat.dto.util.GenericUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MenuFacadeImpl extends FacadeBase implements MenuFacade {

	@Autowired
	private MenuOpcionService menuOpcionService;

	@Autowired
	private MenuMaestroService menuMaestroService;

	@Autowired
	private MenuMaestroMapper menuMaestroMapper;

	@Autowired
	private MenuOpcionMapper menuOpcionMapper;

	@Override
	public MenuMaestroResponse get(UUID menuMaestroId) {
		MenuMaestro menuMaestro = menuMaestroService.get(menuMaestroId);
		MenuMaestroResponse response = menuMaestroMapper.map(menuMaestro);
		if (GenericUtil.isNotNull(menuMaestro.getMenuMaestro())) {
			response.setReferenciaId(menuMaestro.getMenuMaestro().getMenuMaestroId());
		}
		return response;
	}

	@Override
	public MenuOpcionResponse getOpcion(UUID menuOpcionId) {
		MenuOpcion menuOpcion = menuOpcionService.get(menuOpcionId);
		MenuOpcionResponse response = menuOpcionMapper.map(menuOpcion);
		response.setMenuMaestroId(menuOpcion.getMenuMaestro().getMenuMaestroId());
		return response;
	}

	@Override
	public List<MenuMaestroResponse> load() {
		List<MenuMaestro> collection = menuMaestroService.load();
		List<MenuMaestroResponse> listDto = new ArrayList<>();
		collection.forEach(tt -> {
			MenuMaestroResponse response = menuMaestroMapper.map(tt);
			List<MenuMaestroItemResponse> items = this.loadItem(tt.getMenuMaestroId());
			response.setItems(items);
			listDto.add(response);
		});
		return listDto;
	}

	@Override
	public List<MenuMaestroItemResponse> loadItem(UUID referenciaId) {
		List<MenuMaestro> collection = menuMaestroService.loadItem(referenciaId);
		List<MenuMaestroItemResponse> listDto = new ArrayList<>();
		collection.forEach(tt -> {
			MenuMaestroItemResponse response = menuMaestroMapper.mapMenuItem(tt);
			response.setReferenciaId(tt.getMenuMaestro().getMenuMaestroId());
			listDto.add(response);
		});
		return listDto;
	}

	@Override
	public List<MenuOpcionResponse> loadOpcion(UUID menuMaestroId) {
		List<MenuOpcion> collection = menuOpcionService.load(menuMaestroId);
		List<MenuOpcionResponse> listDto = new ArrayList<>();
		collection.forEach(tt -> {
			MenuOpcionResponse response = menuOpcionMapper.map(tt);
			response.setMenuMaestroId(tt.getMenuMaestro().getMenuMaestroId());
			listDto.add(response);
		});
		return listDto;
	}

	@Override
	public BaseOperacionResponse saveOrUpdate(MenuMaestroRequest request) {
		menuMaestroService.saveOrUpdate(request);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public BaseOperacionResponse saveOrUpdateOpcion(MenuOpcionRequest request) {
		menuOpcionService.saveOrUpdate(request);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public BaseOperacionResponse delete(UUID menuMaestroId) {
		menuMaestroService.delete(menuMaestroId);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageDelete);
	}

	@Override
	public BaseOperacionResponse deleteOpcion(UUID menuOpcionId) {
		menuOpcionService.delete(menuOpcionId);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageDelete);
	}

}
