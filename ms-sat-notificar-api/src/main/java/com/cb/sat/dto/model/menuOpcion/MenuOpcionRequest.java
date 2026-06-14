package com.cb.sat.dto.model.menuOpcion;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuOpcionRequest implements Serializable {
	private static final long serialVersionUID = 4156826220628650249L;
	private UUID menuOpcionId;
	private String codigo;
	private String nombre;
	private String descripcion;
	private UUID menuMaestroId;
}
