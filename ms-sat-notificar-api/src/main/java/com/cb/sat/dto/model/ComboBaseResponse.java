package com.cb.sat.dto.model;

import java.io.Serializable;
import java.util.List;

import com.cb.sat.dto.util.GenericUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComboBaseResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5575473914142748397L;
	private List<ComboResponse> list;
	private Integer size;

	public ComboBaseResponse(List<ComboResponse> list) {
		this.list = list;
		if (GenericUtil.isNotNull(list)) {
			this.size = list.size();
		}
	}
}
