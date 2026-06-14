package com.cb.sat.dto.model.rol;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolResponse implements Serializable {
	private static final long serialVersionUID = 3454063242889336494L;
	private UUID rolId;
	private String codigo;
	private String nombre;
	private String descripcion;
	private Boolean habilitado;
}
