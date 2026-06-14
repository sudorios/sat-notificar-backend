package com.cb.sat.dto.model.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioRecuperarClaveRequest implements Serializable {
	
	private static final long serialVersionUID = 5556071315364762273L;
	private String correo;
	
}
