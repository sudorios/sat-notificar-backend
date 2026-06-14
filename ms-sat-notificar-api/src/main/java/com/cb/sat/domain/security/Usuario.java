package com.cb.sat.domain.security;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cb.sat.core.audit.BaseEntity;
import com.cb.sat.domain.configuration.Empresa;
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
@Table(name = "usuario", schema = Constantes.SCHEMA, catalog = Constantes.CATALOG)
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = -2197474493652118156L;

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "usuario_id", unique = true, nullable = false)
	private UUID usuarioId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rol_id")
	private Rol rol;

	@Column(name = "tipo_documento_codigo")
	private String tipoDocumentoCodigo;
	@Column(name = "documento")
	private String documento;
	@Column(name = "usuario")
	private String usuario;
	@Column(name = "password")
	private String password;
	@Column(name = "nombre")
	private String nombres;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;

	public Usuario(UUID usuarioId) {
		this.usuarioId = usuarioId;
	}

}
