package com.cb.sat.app.security.service;

import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.security.MenuRol;
import com.cb.sat.domain.security.view.MenuRolDataGrid;


public interface MenuRolService {

	List<MenuRolDataGrid> loadMenuByRolId(String rolCodigo);
	
	List<MenuRolDataGrid> loadMenuHijo(String rolCodigo, UUID menuMaestroId);
	
	MenuRol get(UUID menuRolId);
}
