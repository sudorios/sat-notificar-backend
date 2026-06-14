package com.cb.sat.dto.model.empresa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaDataGridResponse implements Serializable {
	
	private static final long serialVersionUID = 5184859465144495783L;
	private UUID empresaId;
	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private String direccion;
	private String descripcion;
	private LocalDateTime fechaRegistro;
	private String estadoCodigo;
	private String estado;
}
