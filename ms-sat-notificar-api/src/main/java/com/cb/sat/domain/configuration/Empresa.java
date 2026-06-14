package com.cb.sat.domain.configuration;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cb.sat.core.audit.BaseEntity;
import com.cb.sat.domain.security.Usuario;

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
@Table(name = "empresa", schema = Constantes.SCHEMA)
public class Empresa extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8396278958861567227L;
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "empresa_id", unique = true, nullable = false)
	private UUID empresaId;

	@Column(name = "ruc")
	private String ruc;

	@Column(name = "razon_social")
	private String razonSocial;

	@Column(name = "nombre_comercial")
	private String nombreComercial;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro;

	@Column(name = "estado_codigo")
	private String estadoCodigo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	private List<Usuario> usuarios;

	public Empresa(UUID empresaId) {
		this.empresaId = empresaId;
	}

}
