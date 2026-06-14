package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpcionBotonResponse implements Serializable {
	
	private static final long serialVersionUID = -4573797803754009603L;
	private UUID botonId;
	private String codigoBoton;
	private String nombreBoton;
	private String descripcion;
	private Boolean habilitado;
	private UUID menuMaestroId;
	
}
