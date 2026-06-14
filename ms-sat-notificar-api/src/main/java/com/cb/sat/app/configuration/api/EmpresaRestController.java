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

import com.cb.sat.app.configuration.facade.EmpresaFacade;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.empresa.EmpresaComboResponse;
import com.cb.sat.dto.model.empresa.EmpresaDataGridResponse;
import com.cb.sat.dto.model.empresa.EmpresaFiltroRequest;
import com.cb.sat.dto.model.empresa.EmpresaRequest;
import com.cb.sat.dto.model.empresa.EmpresaResponse;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaRestController {

	@Autowired
	private EmpresaFacade empresaFacade;

	@GetMapping("/init")
	public EmpresaComboResponse init() {
		return empresaFacade.init();
	}

	@GetMapping("/get/{empresaId}")
	public EmpresaResponse get(@PathVariable UUID empresaId) {
		return empresaFacade.get(empresaId);
	}

	@PostMapping("/find")
	public CollectionResponse<EmpresaDataGridResponse> find(@RequestBody EmpresaFiltroRequest request) {
		return empresaFacade.find(request);
	}

	@PostMapping("/saveOrUpdate")
	public BaseOperacionResponse saveOrUpdate(@RequestBody EmpresaRequest request) {
		return empresaFacade.saveOrUpdate(request);
	}

	@DeleteMapping("/delete/{empresaId}")
	public BaseOperacionResponse delete(@PathVariable UUID empresaId) {
		return empresaFacade.delete(empresaId);
	}

}
