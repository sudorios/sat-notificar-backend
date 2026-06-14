package com.cb.sat.dto.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseRequest implements Serializable {
	
	private static final long serialVersionUID = 4757422957624732426L;
	private Integer start = Constantes.PAGINATION_START;
	private Integer limit = Constantes.PAGINATION_SIZE;
	private String sort;
	private String palabraClave;
	private Long totalCount;
	
}
