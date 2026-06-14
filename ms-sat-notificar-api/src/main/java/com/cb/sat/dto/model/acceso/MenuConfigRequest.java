package com.cb.sat.dto.model.acceso;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuConfigRequest implements Serializable {
	
	private static final long serialVersionUID = -4105309432816620245L;
	private List<MenuRequest> menusConfig;
	
}
