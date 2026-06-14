package com.cb.sat.app.security.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cb.sat.domain.security.view.UsuarioDataGrid;

@Repository
public interface UsuarioFiltroRepository
		extends JpaRepository<UsuarioDataGrid, UUID>, JpaSpecificationExecutor<UsuarioDataGrid> {
	@Query("SELECT t FROM UsuarioDataGrid t WHERE t.usuario=:usuario ")
	Optional<UsuarioDataGrid> loadUserByUsername(@Param("usuario") String usuario);

	@Query("SELECT t FROM UsuarioDataGrid t  WHERE t.token=:token")
	Optional<UsuarioDataGrid> getByToken(@Param("token") String token);
}
