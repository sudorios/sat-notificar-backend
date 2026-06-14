package com.cb.sat.app.security.service;

import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.security.Usuario;
import com.cb.sat.domain.security.view.UsuarioDataGrid;
import com.cb.sat.dto.model.auth.CambioPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioCambioPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioFiltroRequest;
import com.cb.sat.dto.model.usuario.UsuarioRequest;
import com.cb.sat.dto.model.usuario.UsuarioResetPasswordRequest;

public interface UsuarioService {
	
	UsuarioDataGrid loadUserByUsername(String usuario);

	void updatePasswordExternal(CambioPasswordRequest t);

	Usuario getByToken(String token);

	Usuario getByCorreo(String correo);

	void update(Usuario usuario);

	void saveOrUpdate(UsuarioRequest t);

	Usuario get(UUID usuarioId);

	List<UsuarioDataGrid> find(UsuarioFiltroRequest t);

	void updatePassword(UsuarioCambioPasswordRequest t);

	void resetPasword(UsuarioResetPasswordRequest t);

	UsuarioDataGrid getById(UUID usuarioId);
	
}