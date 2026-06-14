package com.cb.sat.domain.security.view;

import java.math.BigDecimal;
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
@Table(name = "vw_menu_rol", catalog = Constantes.CATALOG, schema = Constantes.SCHEMA)
public class MenuRolDataGrid {

	@Id
	@Column(name = "MENU_ROL_ID")
	private UUID menuRolId;
	@Column(name = "MENU_MAESTRO_ID")
	private UUID menuMaestroId;
	@Column(name = "ROL_ID")
	private UUID rolId;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Column(name = "URL")
	private String url;
	@Column(name = "ICONO")
	private String icono;
	@Column(name = "ORDEN")
	private BigDecimal orden;
	@Column(name = "REFERENCIA_ID")
	private UUID referenciaId;
	@Column(name = "ROL_CODIGO")
	private String rolCodigo;
	@Column(name = "HABILITADO")
	private Boolean habilitado;
}
