package com.cb.sat.core.audit;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.cb.sat.dto.model.SesionResponse;

@Component("userSesion")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSesion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4250542966216980858L;

	private SesionResponse registro;

	public UserSesion() {
		registro = new SesionResponse();
	}

	public SesionResponse getRegistro() {
		return registro;
	}

	public void setRegistro(SesionResponse registro) {
		this.registro = registro;
	}

}
