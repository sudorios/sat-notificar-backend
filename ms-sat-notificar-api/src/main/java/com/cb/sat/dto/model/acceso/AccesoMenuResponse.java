package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccesoMenuResponse implements Serializable {
	
	private static final long serialVersionUID = 158766852202427228L;
	private List<AccesoMenuItemResponse> accesoMenu;
	
}
