package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolMenuConfigResponse implements Serializable {
	
	private static final long serialVersionUID = 4305522237011105874L;
	private List<MenuConfigResponse> menusConfig;
}
