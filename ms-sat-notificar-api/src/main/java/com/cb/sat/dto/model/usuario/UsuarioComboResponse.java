package com.cb.sat.dto.model.usuario;

import java.io.Serializable;

import com.cb.sat.dto.model.ComboBaseResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComboResponse implements Serializable {
	
	private static final long serialVersionUID = -6985155206326407556L;
	private ComboBaseResponse tipoDocumento;
	private ComboBaseResponse estado;
	private ComboBaseResponse rol;
	private ComboBaseResponse empresa;
	
}
