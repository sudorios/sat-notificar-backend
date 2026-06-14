package com.cb.sat.app.security.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cb.sat.app.security.facade.AccesoFacade;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.acceso.AccesoRolResponse;
import com.cb.sat.dto.model.acceso.MenuConfigRequest;
import com.cb.sat.dto.model.acceso.RolMenuConfigResponse;
import com.cb.sat.dto.model.acceso.UsuarioAccesoMenuRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoRequest;
import com.cb.sat.dto.model.acceso.UsuarioAccesoResponse;

@RestController
@RequestMapping("/api/acceso")
public class AccesoRestController {
	
	@Autowired
	private AccesoFacade usuarioAccesoFacade;

	@PostMapping("/saveOrUpdate")
	public BaseOperacionResponse saveOrUpdate(@RequestBody UsuarioAccesoRequest request) {
		return usuarioAccesoFacade.saveOrUpdate(request);
	}

	@PostMapping("/saveOrUpdateAccesoBtns")
	public BaseOperacionResponse saveOrUpdateAccesoBtns(@RequestBody UsuarioAccesoMenuRequest request) {
		return usuarioAccesoFacade.saveOrUpdateAccesoBtns(request);
	}

	@GetMapping("/getMenu/{rolId}")
	public RolMenuConfigResponse getMenu(@PathVariable UUID rolId) {
		return usuarioAccesoFacade.getMenu(rolId);
	}
	
	@PostMapping("/saveOrUpdateMenuRol")
	public BaseOperacionResponse saveOrUpdateMenuRol(@RequestBody MenuConfigRequest request) {
		return usuarioAccesoFacade.saveOrUpdateMenuRol(request);
	}
	
	@GetMapping("/get/{rolId}")
	public UsuarioAccesoResponse get(@PathVariable UUID rolId) {
		return usuarioAccesoFacade.get(rolId);
	}
	
	@GetMapping("/check/{menuId}")
	public AccesoRolResponse check(@PathVariable UUID menuId) {
		return usuarioAccesoFacade.check(menuId); 
	}

}
