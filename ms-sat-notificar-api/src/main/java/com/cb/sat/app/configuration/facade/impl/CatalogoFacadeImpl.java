package com.cb.sat.app.configuration.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cb.sat.app.configuration.facade.CatalogoFacade;
import com.cb.sat.app.configuration.service.CatalogoService;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.domain.configuration.Catalogo;
import com.cb.sat.domain.configuration.mappers.CatalogoMapper;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.catalogo.CatalogoFiltroRequest;
import com.cb.sat.dto.model.catalogo.CatalogoRequest;
import com.cb.sat.dto.model.catalogo.CatalogoResponse;
import com.cb.sat.dto.util.GenericUtil;

@Component
public class CatalogoFacadeImpl extends FacadeBase implements CatalogoFacade {
	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private CatalogoMapper catalogoMapper;

	@Override
	public CollectionResponse<CatalogoResponse> find(CatalogoFiltroRequest t) {
		List<CatalogoResponse> collection = new ArrayList<>();
		List<Catalogo> listDTO = catalogoService.find(t);
		listDTO.forEach(tt -> {
			collection.add(catalogoMapper.map(tt));
		});
		return new CollectionResponse<>(collection, t.getStart(), t.getLimit(), t.getTotalCount());
	}

	@Override
	public CollectionResponse<CatalogoResponse> findItem(CatalogoFiltroRequest t) {
		List<CatalogoResponse> collection = new ArrayList<>();
		List<Catalogo> listDTO = catalogoService.findItem(t);
		listDTO.forEach(tt -> {
			collection.add(catalogoMapper.map(tt));
		});
		return new CollectionResponse<>(collection, t.getStart(), t.getLimit(), t.getTotalCount());
	}

	@Override
	public CollectionResponse<CatalogoResponse> search(CatalogoFiltroRequest t) {
		List<CatalogoResponse> collection = new ArrayList<>();
		List<Catalogo> listDTO = catalogoService.search(t);
		listDTO.forEach(tt -> {
			collection.add(catalogoMapper.map(tt));
		});
		return new CollectionResponse<>(collection, t.getStart(), t.getLimit(), t.getTotalCount());
	}

	@Override
	public CatalogoResponse get(UUID catalogoId) {
		Catalogo catalogo = catalogoService.get(catalogoId);
		CatalogoResponse catalogoResponse = catalogoMapper.map(catalogo);
		return catalogoResponse;
	}

	@Override
	public BaseOperacionResponse delete(UUID catalogoId) {
		catalogoService.delete(catalogoId);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageDelete);
	}

	@Override
	public BaseOperacionResponse saveOrUpdate(CatalogoRequest t) {
		GenericUtil.toUpperCase(t);
		catalogoService.saveOrUpdate(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}
}
