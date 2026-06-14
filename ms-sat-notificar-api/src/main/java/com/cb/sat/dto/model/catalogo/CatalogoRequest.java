package com.cb.sat.dto.model.catalogo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatalogoRequest implements Serializable {
	private static final long serialVersionUID = 3135552380475887072L;

	private UUID catalogoId;
	private String codigo;
	private String nombre;
	private String descripcion;
	private BigDecimal orden;
	private String referenciaCodigo;
	private String valor1;
	private String valor2;
	private String prefijo;
}