package com.cb.sat.app.security.service;

import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.security.MenuOpcion;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionRequest;

public interface MenuOpcionService {

	MenuOpcion get(UUID menuOpcionId);
	
	List<MenuOpcion> load(UUID menuMaestroId);
	
	void saveOrUpdate(MenuOpcionRequest request);
	
	void delete(UUID menuOpcionId);
}
