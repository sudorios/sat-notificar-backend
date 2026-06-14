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
public class AccesoMenuItemResponse implements Serializable {

	private static final long serialVersionUID = -2534203170364294029L;
	private String nombreMenuMaestro;
	private UUID menuMaestroId;
	private String descripcionMenuMaestro;
	private Boolean habilitado;
	private List<OpcionBotonResponse> opcionBoton;
}
