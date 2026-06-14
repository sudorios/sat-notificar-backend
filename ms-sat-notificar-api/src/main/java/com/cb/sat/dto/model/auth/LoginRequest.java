package com.cb.sat.dto.model.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -5472283853968193515L;
	private String usuario;
	private String password;

	public LoginRequest() {
		super();
	}

}