package com.cb.sat.dto.model.menuMaestro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuMaestroResponse implements Serializable {
	private static final long serialVersionUID = -2695710140393407113L;
	private UUID menuMaestroId;
	private UUID referenciaId;
	private String nombre;
	private String descripcion;
	private String url;
	private String icono;
	private BigDecimal orden;
	private List<MenuMaestroItemResponse> items;
}
