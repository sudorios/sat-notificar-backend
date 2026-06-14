package com.cb.sat.app.configuration.facade;

import java.util.UUID;

import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.empresa.EmpresaComboResponse;
import com.cb.sat.dto.model.empresa.EmpresaDataGridResponse;
import com.cb.sat.dto.model.empresa.EmpresaFiltroRequest;
import com.cb.sat.dto.model.empresa.EmpresaRequest;
import com.cb.sat.dto.model.empresa.EmpresaResponse;

public interface EmpresaFacade {
	
	EmpresaComboResponse init();

	EmpresaResponse get(UUID empresaId);

	CollectionResponse<EmpresaDataGridResponse> find(EmpresaFiltroRequest request);

	BaseOperacionResponse saveOrUpdate(EmpresaRequest request);

	BaseOperacionResponse delete(UUID empresaId);
	
}