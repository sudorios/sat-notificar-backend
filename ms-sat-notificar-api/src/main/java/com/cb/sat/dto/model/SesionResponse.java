package com.cb.sat.dto.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SesionResponse implements Serializable {
	
	private static final long serialVersionUID = 4657147503575271756L;
	private String usuario;
	private UUID usuarioId;
	private String rolCodigo;
	
}
