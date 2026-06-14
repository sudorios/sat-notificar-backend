package com.cb.sat.app.configuration.facade;

import java.util.UUID;

import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.catalogo.CatalogoFiltroRequest;
import com.cb.sat.dto.model.catalogo.CatalogoRequest;
import com.cb.sat.dto.model.catalogo.CatalogoResponse;

public interface CatalogoFacade {

	CollectionResponse<CatalogoResponse> find(CatalogoFiltroRequest request);

	CollectionResponse<CatalogoResponse> findItem(CatalogoFiltroRequest request);

	CollectionResponse<CatalogoResponse> search(CatalogoFiltroRequest reqsuest);

	CatalogoResponse get(UUID catalogoId);

	BaseOperacionResponse delete(UUID catalogoId);

	BaseOperacionResponse saveOrUpdate(CatalogoRequest request);

}
