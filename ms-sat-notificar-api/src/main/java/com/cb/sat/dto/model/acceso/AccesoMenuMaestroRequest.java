package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccesoMenuMaestroRequest implements Serializable {
	
	private static final long serialVersionUID = 7680127290571403651L;
	private Boolean habilitado;
	private UUID menuMaestroId;
	
}
