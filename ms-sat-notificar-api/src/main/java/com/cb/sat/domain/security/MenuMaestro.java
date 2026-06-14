package com.cb.sat.domain.security;

import java.math.BigDecimal;
import java.util.List;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu_maestro", schema = Constantes.SCHEMA, catalog = Constantes.CATALOG)
public class MenuMaestro extends BaseEntity {

	private static final long serialVersionUID = -2197474493652118156L;

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "menu_maestro_id", unique = true, nullable = false)
	private UUID menuMaestroId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referencia_id")
	private MenuMaestro menuMaestro;

	@Column(name = "nombre")
	private String nombre;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "url")
	private String url;
	@Column(name = "icono")
	private String icono;
	@Column(name = "orden")
	private BigDecimal orden;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menuMaestro")
	private List<MenuMaestro> menuMaestros;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menuMaestro")
	private List<MenuRol> menuRoles;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menuMaestro")
	private List<MenuOpcion> menuOpciones;

	public MenuMaestro(UUID menuMaestroId) {
		super();
		this.menuMaestroId = menuMaestroId;
	}

}
