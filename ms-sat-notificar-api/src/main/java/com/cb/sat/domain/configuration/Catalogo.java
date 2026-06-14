package com.cb.sat.domain.configuration;

import java.util.UUID;

import com.cb.sat.core.audit.BaseEntity;
import com.cb.sat.dto.model.Constantes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "catalogo", schema = Constantes.SCHEMA)
public class Catalogo extends BaseEntity {

	private static final long serialVersionUID = 3135552380475887072L;

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "catalogo_id", unique = true, nullable = false)
	private UUID catalogoId;

	@Column(name = "codigo", length = 50)
	private String codigo;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "orden")
	private Integer orden;
	@Column(name = "referencia_codigo", length = 50)
	private String referenciaCodigo;
	@Column(name = "valor1")
	private String valor1;
	@Column(name = "valor2")
	private String valor2;
	@Column(name = "prefijo", length = 10)
	private String prefijo;
}
