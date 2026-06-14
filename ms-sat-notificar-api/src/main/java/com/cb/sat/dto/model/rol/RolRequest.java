package com.cb.sat.dto.model.rol;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolRequest implements Serializable {

	private static final long serialVersionUID = -3783006589140974134L;
	private UUID rolId;
	private String codigo;
	private String nombre;
	private String descripcion;

	public RolRequest(UUID rolId) {
		super();
		this.rolId = rolId;
	}
}
