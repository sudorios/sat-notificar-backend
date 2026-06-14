package com.cb.sat.app.configuration.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cb.sat.app.configuration.facade.ComunFacade;
import com.cb.sat.app.configuration.facade.EmpresaFacade;
import com.cb.sat.app.configuration.service.EmpresaService;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.domain.configuration.Empresa;
import com.cb.sat.domain.configuration.mappers.EmpresaMapper;
import com.cb.sat.domain.configuration.view.EmpresaDataGrid;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.empresa.EmpresaComboResponse;
import com.cb.sat.dto.model.empresa.EmpresaDataGridResponse;
import com.cb.sat.dto.model.empresa.EmpresaFiltroRequest;
import com.cb.sat.dto.model.empresa.EmpresaRequest;
import com.cb.sat.dto.model.empresa.EmpresaResponse;
import com.cb.sat.dto.util.GenericUtil;

@Component
public class EmpresaFacadeImpl extends FacadeBase implements EmpresaFacade {

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ComunFacade comunFacade;

	@Autowired
	private EmpresaMapper empresaMapper;

	@Override
	public EmpresaComboResponse init() {
		EmpresaComboResponse combo = new EmpresaComboResponse();
		combo.setEstado(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.ESTADO_EMPRESA));
		return combo;
	}

	@Override
	public EmpresaResponse get(UUID empresaId) {
		Empresa empresa = empresaService.get(empresaId);
		EmpresaResponse response = empresaMapper.map(empresa);
		EmpresaComboResponse combo = new EmpresaComboResponse();
		combo.setEstado(comunFacade.loadByReferenciaNombre(Constantes.Catalogo.ESTADO_EMPRESA));
		response.setCombo(combo);
		return response;
	}

	@Override
	public CollectionResponse<EmpresaDataGridResponse> find(EmpresaFiltroRequest t) {
		List<EmpresaDataGrid> collection = empresaService.find(t);
		List<EmpresaDataGridResponse> listDto = new ArrayList<>();
		collection.forEach(tt -> {
			listDto.add(empresaMapper.map(tt));
		});
		return new CollectionResponse<>(listDto, t.getStart(), t.getLimit(), t.getTotalCount());
	}

	@Override
	public BaseOperacionResponse saveOrUpdate(EmpresaRequest t) {
		GenericUtil.toUpperCase(t);
		empresaService.saveOrUpdate(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public BaseOperacionResponse delete(UUID empresaId) {
		empresaService.delete(empresaId);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageDelete);
	}

}
