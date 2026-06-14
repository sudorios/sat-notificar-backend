package com.cb.sat.app.security.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cb.sat.domain.security.Rol;

public interface RolRepository extends JpaRepository<Rol, UUID>, JpaSpecificationExecutor<Rol> {

	@Query("SELECT t FROM Rol t WHERE t.codigo=:codigo")
	Optional<Rol> getByCodigo(@Param("codigo") String rolCodigo);

}