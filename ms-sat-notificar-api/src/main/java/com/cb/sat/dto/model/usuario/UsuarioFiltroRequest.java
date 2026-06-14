package com.cb.sat.dto.model.usuario;

import java.io.Serializable;
import java.util.UUID;

import com.cb.sat.dto.model.BaseRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioFiltroRequest extends BaseRequest implements Serializable {
	
	private static final long serialVersionUID = -1581556325765299800L;
	private String nombreCompleto;
	private String numeroDocumento;
	private String tipoDocumentoCodigo;
	private String estadoCodigo;
	private UUID rolId;
	
}
