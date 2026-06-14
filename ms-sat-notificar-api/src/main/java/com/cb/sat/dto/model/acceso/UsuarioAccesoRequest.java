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
public class UsuarioAccesoRequest implements Serializable {
	
	
	private static final long serialVersionUID = 8670468570348349334L;
	private UUID rolId;
	private List<AccesoPerfilRequest> accesoPerfil;
	private List<AccesoMenuMaestroRequest> menuMaestroRequest;
}
