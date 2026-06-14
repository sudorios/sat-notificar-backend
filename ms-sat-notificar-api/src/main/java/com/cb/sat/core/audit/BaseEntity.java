package com.cb.sat.core.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1456252670018598060L;

	@Column(name = "habilitado")
	private Boolean habilitado;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "modificado_por")
	private String modificadoPor;

	@Column(name = "creado")
	private LocalDateTime creado;

	@Column(name = "modificado")
	private LocalDateTime modificado;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
