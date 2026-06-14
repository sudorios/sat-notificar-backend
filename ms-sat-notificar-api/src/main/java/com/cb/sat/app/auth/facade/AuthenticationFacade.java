package com.cb.sat.app.auth.facade;


import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.auth.AutorizacionResponse;
import com.cb.sat.dto.model.auth.CambioPasswordRequest;
import com.cb.sat.dto.model.auth.LoginRequest;
import com.cb.sat.dto.model.auth.UsuarioRecuperarClaveRequest;

public interface AuthenticationFacade {

	AutorizacionResponse login(LoginRequest request);

	BaseOperacionResponse updatePassword(CambioPasswordRequest request);

	BaseOperacionResponse recuperarClave(UsuarioRecuperarClaveRequest request);

}