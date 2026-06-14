package com.cb.sat.dto.model.usuario;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDataGridResponse implements Serializable {

	private static final long serialVersionUID = 8117788652068623940L;
	private UUID usuarioId;
	private UUID rolId;
	private String rolCodigo;
	private String rol;
	private String usuario;
	private String nombreCompleto;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correo;
	private String telefono;
	private String estadoCodigo;
	private String estado;
	private String tipoDocumentoCodigo;
	private String tipoDocumento;
	private String documento;
}
