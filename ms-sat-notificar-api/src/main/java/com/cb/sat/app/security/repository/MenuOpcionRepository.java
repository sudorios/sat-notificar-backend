package com.cb.sat.app.security.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cb.sat.domain.security.MenuOpcion;

public interface MenuOpcionRepository extends JpaRepository<MenuOpcion, UUID>, JpaSpecificationExecutor<MenuOpcion> {
	@Query("SELECT adg FROM MenuOpcion adg   WHERE adg.menuMaestro.menuMaestro = :menuReferenciaId")
	List<MenuOpcion> findByMenuOpcion(@Param("menuReferenciaId") UUID menuReferenciaId);

	@Query("SELECT adg FROM MenuOpcion adg  WHERE adg.habilitado = :habilitado")
	List<MenuOpcion> findByMenuOpcionActivo(@Param("habilitado") Boolean habilitado);
}
