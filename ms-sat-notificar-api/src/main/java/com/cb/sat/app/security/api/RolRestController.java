package com.cb.sat.app.security.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cb.sat.app.security.facade.RolFacade;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.rol.RolFiltroRequest;
import com.cb.sat.dto.model.rol.RolRequest;
import com.cb.sat.dto.model.rol.RolResponse;

@RestController
@RequestMapping("/api/rol")
public class RolRestController {

	@Autowired
	private RolFacade rolFacade;

	@PostMapping("/saveOrUpdate")
	public BaseOperacionResponse saveOrUpdate(@RequestBody RolRequest request) {
		return rolFacade.saveOrUpdate(request);
	}

	@GetMapping("/get/{rolId}")
	public RolResponse get(@PathVariable UUID rolId) {
		return rolFacade.get(rolId);
	}

	@PostMapping("/find")
	public CollectionResponse<RolResponse> find(@RequestBody RolFiltroRequest request) {
		return rolFacade.find(request);
	}

	@DeleteMapping("/delete/{rolId}")
	public BaseOperacionResponse delete(@PathVariable UUID rolId) {
		return rolFacade.delete(rolId);
	}

}
