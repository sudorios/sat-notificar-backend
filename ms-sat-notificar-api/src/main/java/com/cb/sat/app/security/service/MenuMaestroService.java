package com.cb.sat.app.security.service;

import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.security.MenuMaestro;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroRequest;

public interface MenuMaestroService {

	MenuMaestro get(UUID menuMaestroId);

	List<MenuMaestro> load();

	List<MenuMaestro> loadItem(UUID referenciaId);

	void saveOrUpdate(MenuMaestroRequest request);

	void delete(UUID menuMaestroId);

}
