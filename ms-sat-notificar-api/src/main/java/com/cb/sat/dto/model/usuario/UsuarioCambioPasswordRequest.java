package com.cb.sat.dto.model.usuario;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioCambioPasswordRequest implements Serializable {

	private static final long serialVersionUID = 1991328859024252778L;
	private String claveActual;
	private String nuevoPassword;
	private String confirmarPassword;

}
