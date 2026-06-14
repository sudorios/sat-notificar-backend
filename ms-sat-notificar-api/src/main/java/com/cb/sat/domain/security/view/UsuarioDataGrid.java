package com.cb.sat.domain.security.view;

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
@Table(name = "vw_usuario", schema = Constantes.SCHEMA, catalog = Constantes.CATALOG)
public class UsuarioDataGrid implements Serializable {
	
	private static final long serialVersionUID = 857277710403456509L;
	@Id
	@Column(name = "usuario_id")
	private UUID usuarioId;

	@Column(name = "rol_id")
	private UUID rolId;

	@Column(name = "rol_codigo")
	private String rolCodigo;

	@Column(name = "rol")
	private String rol;

	@Column(name = "usuario")
	private String usuario;

	@Column(name = "password")
	private String password;

	@Column(name = "nombre_completo")
	private String nombreCompleto;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@Column(name = "apellido_materno")
	private String apellidoMaterno;

	@Column(name = "correo")
	private String correo;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "token")
	private String token;

	@Column(name = "fecha_expiracion_token")
	private LocalDateTime fechaExpiracionToken;

	@Column(name = "estado_codigo")
	private String estadoCodigo;

	@Column(name = "estado")
	private String estado;

	@Column(name = "fecha_consulta")
	private LocalDateTime fechaConsulta;

	@Column(name = "tipo_documento_codigo")
	private String tipoDocumentoCodigo;

	@Column(name = "tipo_documento")
	private String tipoDocumento;

	@Column(name = "documento")
	private String documento;

	@Column(name = "habilitado")
	private Boolean habilitado;
}
