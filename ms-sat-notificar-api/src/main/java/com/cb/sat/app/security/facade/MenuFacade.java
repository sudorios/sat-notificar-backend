package com.cb.sat.app.security.facade;

import java.util.List;
import java.util.UUID;

import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroItemResponse;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroRequest;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroResponse;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionRequest;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionResponse;

public interface MenuFacade {

	MenuMaestroResponse get(UUID menuMaestroId);

	MenuOpcionResponse getOpcion(UUID menuOpcionId);
	
	List<MenuMaestroResponse> load();
	
	List<MenuMaestroItemResponse> loadItem(UUID referenciaId);
	
	List<MenuOpcionResponse> loadOpcion(UUID menuMaestroId);

	BaseOperacionResponse saveOrUpdate(MenuMaestroRequest request);
	
	BaseOperacionResponse saveOrUpdateOpcion(MenuOpcionRequest request);

	BaseOperacionResponse delete(UUID menuMaestroId);
	
	BaseOperacionResponse deleteOpcion(UUID menuOpcionId);
}
