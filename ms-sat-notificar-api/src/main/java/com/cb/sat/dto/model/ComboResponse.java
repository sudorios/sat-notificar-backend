package com.cb.sat.dto.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComboResponse implements Serializable {
	
	private static final long serialVersionUID = -6861706514599906082L;
	private UUID id;
	private String codigo;
	private String nombre;

	public ComboResponse(UUID id, String codigo, String nombre) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public ComboResponse(UUID id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
}
