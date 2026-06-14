package com.cb.sat.app.configuration.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cb.sat.app.configuration.facade.ComunFacade;
import com.cb.sat.app.configuration.service.CatalogoService;
import com.cb.sat.app.configuration.service.EmpresaService;
import com.cb.sat.app.security.service.RolService;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.domain.configuration.Catalogo;
import com.cb.sat.domain.configuration.mappers.CatalogoMapper;
import com.cb.sat.domain.configuration.view.EmpresaDataGrid;
import com.cb.sat.domain.security.Rol;
import com.cb.sat.dto.model.ComboBaseResponse;
import com.cb.sat.dto.model.ComboResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.catalogo.CatalogoResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ComunFacadeImpl extends FacadeBase implements ComunFacade {

	@Autowired
	private RolService rolService;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private CatalogoMapper catalogoMapper;

	@Override
	public ComboBaseResponse loadByReferenciaNombre(String referenciaCodigo) {
		log.info(referenciaCodigo);
		List<ComboResponse> collections = new ArrayList<>();
		List<Catalogo> catalogos = catalogoService.loadByReferenciaNombre(referenciaCodigo);
		for (Catalogo tt : catalogos) {
			collections.add(new ComboResponse(tt.getCatalogoId(), tt.getCodigo(), tt.getNombre()));
		}
		return new ComboBaseResponse(collections);
	}

	@Override
	public ComboBaseResponse loadByReferenciaNombreIn(String referenciaCodigo, List<String> codigos) {
		List<ComboResponse> collections = new ArrayList<>();
		List<Catalogo> catalogos = catalogoService.loadByReferenciaNombreIn(referenciaCodigo, codigos);
		for (Catalogo tt : catalogos) {
			collections.add(new ComboResponse(tt.getCatalogoId(), tt.getCodigo(), tt.getNombre()));
		}
		return new ComboBaseResponse(collections);
	}

	@Override
	public List<CatalogoResponse> listByReferenciaNombre(String referenciaCodigo) {
		log.info(referenciaCodigo);
		List<CatalogoResponse> collections = new ArrayList<>();
		List<Catalogo> catalogos = catalogoService.loadByReferenciaOrden(referenciaCodigo);
		for (Catalogo tt : catalogos) {
			collections.add(catalogoMapper.map(tt));
		}
		return collections;
	}

	@Override
	public ComboBaseResponse loadRol() {
		log.info("::loadRol::");
		List<ComboResponse> collections = new ArrayList<>();
		List<Rol> roles = rolService.load();
		roles.forEach(tt -> {
			collections.add(new ComboResponse(tt.getRolId(), tt.getCodigo(), tt.getNombre()));
		});
		return new ComboBaseResponse(collections);
	}

	@Override
	public ComboBaseResponse loadTipoDocumentoIdentidad() {
		List<ComboResponse> collections = new ArrayList<>();
		Catalogo tt = catalogoService.getCatalogo(Constantes.Catalogo.TIPO_DOCUMENTO, Constantes.TipoDocumento.DNI);
		collections.add(new ComboResponse(tt.getCatalogoId(), tt.getValor2(), tt.getValor1()));
		return new ComboBaseResponse(collections);
	}

	@Override
	public ComboBaseResponse loadEstado() {
		List<ComboResponse> collections = new ArrayList<>();
		List<Catalogo> listDto = catalogoService.loadByReferenciaNombre(Constantes.Catalogo.ESTADO_USUARIO);
		listDto.forEach(tt -> {
			collections.add(new ComboResponse(tt.getCatalogoId(), tt.getCodigo(), tt.getNombre()));
		});
		return new ComboBaseResponse(collections);
	}

	@Override
	public ComboBaseResponse loadEmpresa() {
		List<ComboResponse> listDto = new ArrayList<>();
		List<EmpresaDataGrid> collections = empresaService.load();
		collections.forEach(tt -> {
			listDto.add(new ComboResponse(tt.getEmpresaId(), tt.getRazonSocial()));
		});
		return new ComboBaseResponse(listDto);
	}
}
