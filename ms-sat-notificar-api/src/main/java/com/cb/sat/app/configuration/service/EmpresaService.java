package com.cb.sat.app.configuration.service;

import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.configuration.Empresa;
import com.cb.sat.domain.configuration.view.EmpresaDataGrid;
import com.cb.sat.dto.model.empresa.EmpresaFiltroRequest;
import com.cb.sat.dto.model.empresa.EmpresaRequest;

public interface EmpresaService {

	Empresa get(UUID empresaId);

	List<EmpresaDataGrid> find(EmpresaFiltroRequest request);

	List<EmpresaDataGrid> load();

	void saveOrUpdate(EmpresaRequest request);

	void delete(UUID empresaId);
}
