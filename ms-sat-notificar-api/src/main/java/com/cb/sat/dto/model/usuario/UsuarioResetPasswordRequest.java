package com.cb.sat.dto.model.usuario;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResetPasswordRequest implements Serializable {
	private static final long serialVersionUID = 7768651468853910324L;
	private UUID usuarioId;
}
