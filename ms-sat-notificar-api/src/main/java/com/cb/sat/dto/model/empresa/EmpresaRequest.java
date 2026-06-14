package com.cb.sat.dto.model.empresa;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaRequest implements Serializable {
	
	private static final long serialVersionUID = -7829750847315617993L;
	private UUID empresaId;
	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private String direccion;
	private String descripcion;
	
}
