package com.cb.sat.dto.model.empresa;

import java.io.Serializable;

import com.cb.sat.dto.model.ComboBaseResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaComboResponse implements Serializable {

	private static final long serialVersionUID = -6361952454256517292L;
	private ComboBaseResponse estado;

}