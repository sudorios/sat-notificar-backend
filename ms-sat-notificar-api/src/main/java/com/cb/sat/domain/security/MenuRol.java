package com.cb.sat.domain.security;

import java.util.UUID;

import com.cb.sat.core.audit.BaseEntity;
import com.cb.sat.dto.model.Constantes;

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
@Table(name = "menu_rol", schema = Constantes.SCHEMA, catalog = Constantes.CATALOG)
public class MenuRol extends BaseEntity {

	private static final long serialVersionUID = -2197474493652118156L;

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "menu_rol_id", unique = true, nullable = false)
	private UUID menuRolId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_maestro_id")
	private MenuMaestro menuMaestro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rol_id")
	private Rol rol;

	@Column(name = "habilitado")
	private Boolean habilitado;

}
