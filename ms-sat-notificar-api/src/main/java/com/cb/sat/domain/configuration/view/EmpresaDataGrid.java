package com.cb.sat.domain.configuration.view;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.cb.sat.dto.model.Constantes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "vw_empresa", schema = Constantes.SCHEMA, catalog = Constantes.CATALOG)
public class EmpresaDataGrid implements Serializable {
	
	private static final long serialVersionUID = -6966217565692374689L;

	@Id
	@Column(name = "empresa_id")
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

	@Column(name = "habilitado")
	private Boolean habilitado;

	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro;

	@Column(name = "estado_codigo")
	private String estadoCodigo;

	@Column(name = "estado")
	private String estado;

}