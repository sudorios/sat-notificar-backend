package com.cb.sat.app.configuration.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cb.sat.app.configuration.facade.CatalogoFacade;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.catalogo.CatalogoFiltroRequest;
import com.cb.sat.dto.model.catalogo.CatalogoRequest;
import com.cb.sat.dto.model.catalogo.CatalogoResponse;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoRestController {

	@Autowired
	private CatalogoFacade catalogoFacade;

	@PostMapping("/find")
	public CollectionResponse<CatalogoResponse> find(@RequestBody CatalogoFiltroRequest request) {
		return catalogoFacade.find(request);
	}

	@PostMapping("/findItem")
	public CollectionResponse<CatalogoResponse> findItem(@RequestBody CatalogoFiltroRequest request) {
		return catalogoFacade.findItem(request);
	}

	@PostMapping("/search")
	public CollectionResponse<CatalogoResponse> search(@RequestBody CatalogoFiltroRequest request) {
		return catalogoFacade.search(request);
	}

	@GetMapping("/get/{catalogoId}")
	public CatalogoResponse get(@PathVariable UUID catalogoId) {
		return catalogoFacade.get(catalogoId);
	}

	@DeleteMapping("/delete/{catalogoId}")
	public BaseOperacionResponse delete(@PathVariable UUID catalogoId) {
		return catalogoFacade.delete(catalogoId);
	}

	@PostMapping("/saveOrUpdate")
	public BaseOperacionResponse saveOrUpdate(@RequestBody CatalogoRequest request) {
		return catalogoFacade.saveOrUpdate(request);
	}
}
