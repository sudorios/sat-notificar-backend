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
public class MenuRequest implements Serializable {
	
	private static final long serialVersionUID = -5935350067636233444L;
	private UUID rolId;
	private UUID menuMaestroId;
	private UUID menuRolId;
	private Boolean habilitado;
	private List<SubMenuConfigRequest> subMenus;
	
}
