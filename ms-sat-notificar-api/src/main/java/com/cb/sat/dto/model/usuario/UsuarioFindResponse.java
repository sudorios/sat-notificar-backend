package com.cb.sat.dto.model.usuario;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioFindResponse implements Serializable {

	private static final long serialVersionUID = 7930265287974224537L;
	private UUID usuarioId;
	private String usuario;
	private String nombreCompleto;
	private UUID rolId;
	private String rol;
	private String rolCodigo;
	private String tipoDocumentoCodigo;
	private String documento;
	private String estadoCodigo;
	private String estado;
	private String correo;
	private String telefono;

}
