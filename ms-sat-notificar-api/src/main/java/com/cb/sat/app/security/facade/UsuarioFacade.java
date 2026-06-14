package com.cb.sat.app.security.facade;

import java.util.UUID;

import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.CollectionResponse;
import com.cb.sat.dto.model.auth.menu.MenuRolResponse;
import com.cb.sat.dto.model.usuario.UsuarioCambioPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioComboResponse;
import com.cb.sat.dto.model.usuario.UsuarioDataGridResponse;
import com.cb.sat.dto.model.usuario.UsuarioFiltroRequest;
import com.cb.sat.dto.model.usuario.UsuarioFindResponse;
import com.cb.sat.dto.model.usuario.UsuarioRequest;
import com.cb.sat.dto.model.usuario.UsuarioResetPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioResponse;

public interface UsuarioFacade {

	BaseOperacionResponse saveOrUpdate(UsuarioRequest request);

	UsuarioResponse initForm();

	UsuarioComboResponse init();

	CollectionResponse<UsuarioFindResponse> find(UsuarioFiltroRequest t);

	MenuRolResponse loadMenu();

	BaseOperacionResponse updatePassword(UsuarioCambioPasswordRequest t);

	BaseOperacionResponse resetPassword(UsuarioResetPasswordRequest request);

	UsuarioResponse get(UUID usuarioId);

	UsuarioDataGridResponse getById(UUID usuarioId);

}
