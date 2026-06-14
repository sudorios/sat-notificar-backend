package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccesoRolResponse implements Serializable {

	private static final long serialVersionUID = -3125064543849857154L;
	private List<AccesoOpcionResponse> opciones;
}
