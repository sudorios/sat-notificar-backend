package com.cb.sat.app.auth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cb.sat.app.auth.facade.AuthenticationFacade;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.auth.AutorizacionResponse;
import com.cb.sat.dto.model.auth.CambioPasswordRequest;
import com.cb.sat.dto.model.auth.LoginRequest;
import com.cb.sat.dto.model.auth.UsuarioRecuperarClaveRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {
	
	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	@PostMapping("/login")
	public AutorizacionResponse login(@RequestBody LoginRequest request) {
		return authenticationFacade.login(request);
	}
	
	@PostMapping("/recuperarClave")
	public BaseOperacionResponse recuperarClave(@RequestBody UsuarioRecuperarClaveRequest request) {
		return authenticationFacade.recuperarClave(request);
	}
	
	@PostMapping("/updatePassword")
	public BaseOperacionResponse updatePassword(@RequestBody CambioPasswordRequest request) {
		return authenticationFacade.updatePassword(request);
	}
	
}
