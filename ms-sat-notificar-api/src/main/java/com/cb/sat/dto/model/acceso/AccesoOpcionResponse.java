package com.cb.sat.dto.model.acceso;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccesoOpcionResponse implements Serializable {

	private static final long serialVersionUID = 4905924133832776923L;
	private String nombre;
	private String codigo;
	private Boolean habilitado;

	public AccesoOpcionResponse(String nombre, String codigo, Boolean habilitado) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.habilitado = habilitado;
	}
}
