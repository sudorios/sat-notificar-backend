package com.cb.sat.app.security.service;

import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.security.Rol;
import com.cb.sat.dto.model.rol.RolFiltroRequest;
import com.cb.sat.dto.model.rol.RolRequest;

public interface RolService {

	List<Rol> load();

	Rol getByCodigo(String rolCodigo);

	List<Rol> loadForUsuario();

	void saveOrUpdate(RolRequest t);
	
	Rol get(UUID rolId);

	List<Rol> find(RolFiltroRequest t);

	void delete(UUID rolId);

}