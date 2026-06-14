package com.cb.sat.dto.model.usuario;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioRequest implements Serializable {

	private static final long serialVersionUID = -3874248674026484645L;
	private UUID usuarioId;
	private UUID rolId;
	private UUID empresaId;
	private String tipoDocumentoCodigo;
	private String documento;
	private String usuario;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correo;
	private String telefono;

}