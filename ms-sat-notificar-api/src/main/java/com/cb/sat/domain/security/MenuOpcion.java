package com.cb.sat.domain.security;

import java.util.List;
import java.util.UUID;

import com.cb.sat.core.audit.BaseEntity;

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
@Table(name = "menu_opcion", schema = "public")
public class MenuOpcion extends BaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = -5436102746248113078L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "menu_opcion_id", unique = true, nullable = false)
	private UUID menuOpcionId;
	
	@Column(name = "codigo", length = 100)
	private String codigo;
	@Column(name = "nombre", length = 250)
	private String nombre;
	@Column(name = "descripcion", length = 250)
	private String descripcion;
	
	@Column(name = "authority", length = 100)
	private String authority;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_maestro_id")
	private MenuMaestro menuMaestro;
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menuOpcion")
	private List<Acceso> accesos;

	 
}
