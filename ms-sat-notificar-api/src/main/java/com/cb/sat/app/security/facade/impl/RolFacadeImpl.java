package com.cb.sat.app.security.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cb.sat.app.security.facade.RolFacade;
import com.cb.sat.app.security.service.RolService;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.domain.security.Rol;
import com.cb.sat.domain.security.mappers.RolMapper;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.rol.RolFiltroRequest;
import com.cb.sat.dto.model.rol.RolRequest;
import com.cb.sat.dto.model.rol.RolResponse;
import com.cb.sat.dto.util.GenericUtil;

@Component
public class RolFacadeImpl extends FacadeBase implements RolFacade {

	@Autowired
	private RolService rolService;

	@Autowired
	private RolMapper rolMapper;

	@Override
	public BaseOperacionResponse saveOrUpdate(RolRequest t) {
		GenericUtil.toUpperCase(t);
		t.setCodigo(GenericUtil.replaceSpaces(t.getCodigo()));
		rolService.saveOrUpdate(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

	@Override
	public RolResponse get(UUID rolId) {
		Rol rol = rolService.get(rolId);
		return rolMapper.map(rol);
	}

	@Override
	public CollectionResponse<RolResponse> find(RolFiltroRequest t) {
		List<RolResponse> collection = new ArrayList<>();
		List<Rol> listDto = rolService.find(t);
		listDto.forEach(tt -> {
			RolResponse response = rolMapper.map(tt);
			collection.add(response);
		});
		return new CollectionResponse<RolResponse>(collection, t.getStart(), t.getLimit(), t.getTotalCount());
	}

	@Override
	public BaseOperacionResponse delete(UUID rolId) {
		rolService.delete(rolId);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
	}

}
