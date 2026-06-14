package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubMenuConfigResponse implements Serializable {
	
	
	private static final long serialVersionUID = -486584679949112858L;
	private UUID menuRolId;
	private String nombre;
	private String descripcion;
	private UUID menuMaestroId;
	private Boolean habilitado;
	
}
