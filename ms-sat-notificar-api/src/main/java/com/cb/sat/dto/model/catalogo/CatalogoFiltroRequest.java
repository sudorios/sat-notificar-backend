package com.cb.sat.dto.model.catalogo;

import java.io.Serializable;

import com.cb.sat.dto.model.BaseRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatalogoFiltroRequest extends BaseRequest implements Serializable {
	private static final long serialVersionUID = -8036311247260297655L;
	private String codigo;
	private String nombre;
	private String referenciaCodigo;
	private String prefijo;
	private String habilitado;
}
