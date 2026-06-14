package com.cb.sat.dto.model.auth;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutorizacionResponse implements Serializable {

	private static final long serialVersionUID = 6159053067640739047L;
	private String usuario;
	private UUID usuarioId;
	private String nombre;
	private String token;
	private String rolCodigo;
	private String rol;
	
}
