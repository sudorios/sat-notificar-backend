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
public class MenuMaestroItemResponse implements Serializable {

	private static final long serialVersionUID = 2443884038533900109L;
	private UUID menuMaestroId;
	private UUID referenciaId;
	private String nombre;
	private String descripcion;
	private String url;
	private BigDecimal orden;
}
