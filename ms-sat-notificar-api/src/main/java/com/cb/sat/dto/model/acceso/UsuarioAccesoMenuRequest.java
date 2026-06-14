package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioAccesoMenuRequest implements Serializable {
	
	private static final long serialVersionUID = 2253856306984551817L;
	private UUID rolId;
	private UUID menuMaestroId;
	private Boolean habilitado;
	private List<AccesoPerfilRequest> accesoPerfil;
	
}
