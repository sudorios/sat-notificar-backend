package com.cb.sat.app.security.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cb.sat.app.security.facade.MenuFacade;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroRequest;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroResponse;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionRequest;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionResponse;

@RestController
@RequestMapping("/api/menu")
public class MenuRestController {

	@Autowired
	private MenuFacade menuFacade;

	@GetMapping("/get/{menuMaestroId}")
	public MenuMaestroResponse get(@PathVariable UUID menuMaestroId) {
		return menuFacade.get(menuMaestroId);
	}
	
	@GetMapping("/getOpcion/{menuOpcionId}")
	public MenuOpcionResponse getOpcion(@PathVariable UUID menuOpcionId) {
		return menuFacade.getOpcion(menuOpcionId);
	}
	
	@GetMapping("/load")
	public List<MenuMaestroResponse> load() {
		return menuFacade.load();
	}
	
	@GetMapping("/loadOpcion/{menuMaestroId}")
	public List<MenuOpcionResponse> loadOpcion(@PathVariable UUID menuMaestroId) {
		return menuFacade.loadOpcion(menuMaestroId);
	}
	
	
	@PostMapping("/saveOrUpdate")
	public BaseOperacionResponse saveOrUpdate(@RequestBody MenuMaestroRequest request) {
		return menuFacade.saveOrUpdate(request);
	}
	
	@PostMapping("/saveOrUpdateOpcion")
	public BaseOperacionResponse saveOrUpdateOpcion(@RequestBody MenuOpcionRequest request) {
		return menuFacade.saveOrUpdateOpcion(request);
	}
	
	@DeleteMapping("/delete/{menuMaestroId}")
	public BaseOperacionResponse delete(@PathVariable UUID menuMaestroId) {
		return menuFacade.delete(menuMaestroId);
	}
	
	@DeleteMapping("/deleteOpcion/{menuOpcionId}")
	public BaseOperacionResponse deleteOpcion(@PathVariable UUID menuOpcionId) {
		return menuFacade.deleteOpcion(menuOpcionId);
	}
}
