package com.cb.sat.domain.security;

import java.util.List;
import java.util.UUID;

import com.cb.sat.core.audit.BaseEntity;
import com.cb.sat.dto.model.Constantes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rol", schema = Constantes.SCHEMA, catalog = Constantes.CATALOG)
public class Rol extends BaseEntity {

	private static final long serialVersionUID = -2197474493652118156L;

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "rol_id", unique = true, nullable = false)
	private UUID rolId;

	@Column(name = "codigo")
	private String codigo;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "descripcion")
	private String descripcion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rol")
	private List<MenuRol> menuRoles;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rol")
	private List<Usuario> usuarios;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rol")
	private List<Acceso> accesos;

	public Rol(UUID rolId) {
		this.rolId = rolId;
	}

}