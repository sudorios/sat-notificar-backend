package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubMenuConfigRequest implements Serializable {
	
	private static final long serialVersionUID = 7210705263852977956L;
	private UUID menuRolId;
	private UUID menuMaestroId;
	private Boolean habilitado;
	
}
