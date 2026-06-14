package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuConfigResponse implements Serializable {
	
	private static final long serialVersionUID = 8157849247148152214L;
	private UUID menuRolId;
	private String nombre;
	private UUID menuMaestroId;
	private UUID rolId;
	private Boolean habilitado;
	private List<SubMenuConfigResponse> subMenus;
	
}
