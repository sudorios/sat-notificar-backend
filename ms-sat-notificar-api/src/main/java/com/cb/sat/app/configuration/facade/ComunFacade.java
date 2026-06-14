package com.cb.sat.app.configuration.facade;

import java.util.List;

import com.cb.sat.dto.model.ComboBaseResponse;
import com.cb.sat.dto.model.catalogo.CatalogoResponse;

public interface ComunFacade {

	ComboBaseResponse loadByReferenciaNombre(String referenciaCodigo);

	ComboBaseResponse loadByReferenciaNombreIn(String referenciaCodigo, List<String> codigos);

	List<CatalogoResponse> listByReferenciaNombre(String referenciaCodigo);

	ComboBaseResponse loadRol();

	ComboBaseResponse loadTipoDocumentoIdentidad();

	ComboBaseResponse loadEstado();

	ComboBaseResponse loadEmpresa();

}
