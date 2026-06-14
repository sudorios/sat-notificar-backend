package com.cb.sat.dto.model.empresa;

import java.io.Serializable;

import com.cb.sat.dto.model.BaseRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaFiltroRequest extends BaseRequest implements Serializable {
	
	private static final long serialVersionUID = 6071400867616662410L;
	private String ruc;
	private String razonSocial;
	private String estadoCodigo;

}