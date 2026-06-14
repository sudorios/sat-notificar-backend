package com.cb.sat.dto.model.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CambioPasswordRequest implements Serializable {
	
	private static final long serialVersionUID = -3105564425436359941L;
	private String nuevoPassword;
	private String confirmarPassword;
	private String token;
}
