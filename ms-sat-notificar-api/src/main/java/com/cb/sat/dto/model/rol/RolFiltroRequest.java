package com.cb.sat.dto.model.rol;

import java.io.Serializable;

import com.cb.sat.dto.model.BaseRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolFiltroRequest extends BaseRequest implements Serializable {
	private static final long serialVersionUID = -8405859957190983694L;
	private String codigo;
	private String nombre;
}
