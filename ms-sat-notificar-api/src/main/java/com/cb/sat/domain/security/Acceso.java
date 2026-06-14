package com.cb.sat.domain.security;

import java.util.UUID;

import com.cb.sat.core.audit.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "acceso", schema = "public")
public class Acceso extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4087408032712241447L;

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "acceso_id", unique = true, nullable = false)
	private UUID accesoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_opcion_id")
	private MenuOpcion menuOpcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rol_id")
	private Rol rol;

}
