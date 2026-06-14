package com.cb.sat.app.security.facade;

import java.util.UUID;

import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.rol.RolFiltroRequest;
import com.cb.sat.dto.model.rol.RolRequest;
import com.cb.sat.dto.model.rol.RolResponse;


public interface RolFacade {

	BaseOperacionResponse saveOrUpdate(RolRequest request);

	RolResponse get(UUID rolId);

	CollectionResponse<RolResponse> find(RolFiltroRequest rolFiltroRequest);

	BaseOperacionResponse delete(UUID rolId);

}
