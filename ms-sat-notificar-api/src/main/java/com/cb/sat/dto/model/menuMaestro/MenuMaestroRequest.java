package com.cb.sat.dto.model.menuMaestro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuMaestroRequest implements Serializable {
	private static final long serialVersionUID = 8989291333376192937L;
	private UUID menuMaestroId;
	private String nombre;
	private String descripcion;
	private String url;
	private String icono;
	private UUID referenciaId;
	private BigDecimal orden;
}
