package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccesoPerfilRequest implements Serializable {
	
	private static final long serialVersionUID = -5704917018403570285L;
	private UUID menuOpcionId;
	private Boolean habilitado;
	
}
