package com.cb.sat.dto.model.empresa;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaResponse implements Serializable {
	
	private static final long serialVersionUID = 6178677240243956674L;
	private UUID empresaId;
	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private String direccion;
	private String descripcion;
	private String estadoCodigo;
	private EmpresaComboResponse combo;

}