package com.cb.sat.dto.model.auth.menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuRolResponse implements Serializable {
	private static final long serialVersionUID = -2091491954967904788L;
	private List<Map<String, Object>> menu;
}
