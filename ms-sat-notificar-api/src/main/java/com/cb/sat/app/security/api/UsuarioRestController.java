package com.cb.sat.app.security.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cb.sat.app.security.facade.UsuarioFacade;
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

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

	@Autowired
	private UsuarioFacade usuarioFacade;

	@GetMapping("/init")
	public UsuarioComboResponse init() {
		return usuarioFacade.init();
	}

	@GetMapping("/initForm")
	public UsuarioResponse initForm() {
		return usuarioFacade.initForm();
	}

	@PostMapping("/find")
	public CollectionResponse<UsuarioFindResponse> find(@RequestBody UsuarioFiltroRequest t) {
		return usuarioFacade.find(t);
	}

	@PostMapping("/saveOrUpdate")
	public BaseOperacionResponse saveOrUpdate(@RequestBody UsuarioRequest request) {
		return usuarioFacade.saveOrUpdate(request);
	}

	@GetMapping("/loadMenu")
	public MenuRolResponse loadMenu() {
		return usuarioFacade.loadMenu();
	}

	@PostMapping("/updatePassword")
	public BaseOperacionResponse updatePassword(@RequestBody UsuarioCambioPasswordRequest t) {
		return usuarioFacade.updatePassword(t);
	}

	@PostMapping("/resetPassword")
	public BaseOperacionResponse resetPassword(@RequestBody UsuarioResetPasswordRequest request) {
		return usuarioFacade.resetPassword(request);
	}

	@GetMapping("/get/{usuarioId}")
	public UsuarioResponse get(@PathVariable UUID usuarioId) {
		return usuarioFacade.get(usuarioId);
	}

	@GetMapping("/getById/{usuarioId}")
	public UsuarioDataGridResponse getById(@PathVariable UUID usuarioId) {
		return usuarioFacade.getById(usuarioId);
	}

}
