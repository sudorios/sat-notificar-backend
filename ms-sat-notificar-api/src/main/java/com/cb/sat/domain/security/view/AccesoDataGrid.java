package com.cb.sat.domain.security.view;

import java.io.Serializable;
import java.util.UUID;

import com.cb.sat.dto.model.Constantes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vw_acceso", schema = Constantes.SCHEMA, catalog = Constantes.CATALOG)
public class AccesoDataGrid implements Serializable {

	private static final long serialVersionUID = 3369577364782580581L;
	@Id
	@Column(name = "acceso_id")
	private UUID accesoId;

	@Column(name = "rol_id")
	private UUID rolId;

	@Column(name = "menu_opcion_id")
	private UUID menuOpcionId;

	@Column(name = "codigo_menu")
	private String codigoMenu;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "habilitado")
	private Boolean habilitado;

	@Column(name = "menu_maestro_id")
	private UUID menuMaestroId;

	@Column(name = "rol")
	private String rol;

	@Column(name = "descripcion")
	private String descripcionBoton;

	@Column(name = "menu_maestro")
	private String menuMaestro;

	@Column(name = "rol_codigo")
	private String rolCodigo;

}
