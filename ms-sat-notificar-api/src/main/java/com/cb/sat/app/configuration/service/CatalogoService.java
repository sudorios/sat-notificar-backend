package com.cb.sat.app.configuration.service;

import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.configuration.Catalogo;
import com.cb.sat.dto.model.catalogo.CatalogoFiltroRequest;
import com.cb.sat.dto.model.catalogo.CatalogoRequest;

public interface CatalogoService {

	List<Catalogo> find(CatalogoFiltroRequest t);

	List<Catalogo> findItem(CatalogoFiltroRequest t);

	List<Catalogo> search(CatalogoFiltroRequest t);

	Catalogo get(UUID catalogoId);

	void delete(UUID catalogoId);

	void saveOrUpdate(CatalogoRequest t);

	List<Catalogo> loadByReferenciaNombre(String referenciaCodigo);

	List<Catalogo> loadByReferenciaNombreIn(String referenciaCodigo, List<String> codigos);

	List<Catalogo> loadByReferenciaOrden(String referenciaCodigo);

	Catalogo getCatalogo(String referenciaCodigo, String codigo);

}