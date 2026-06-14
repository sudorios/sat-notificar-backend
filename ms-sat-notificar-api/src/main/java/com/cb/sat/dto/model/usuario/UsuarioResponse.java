package com.cb.sat.dto.model.usuario;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse implements Serializable {

	private static final long serialVersionUID = -533498928463144888L;
	private UUID usuarioId;
	private UUID rolId;
	private String tipoDocumentoCodigo;
	private String documento;
	private String usuario;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correo;
	private String telefono;
	private String estadoCodigo;
	private UUID empresaId;
	private UsuarioComboResponse combo;
	
}
