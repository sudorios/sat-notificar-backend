package com.cb.sat.app.security.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cb.sat.domain.security.MenuMaestro;

public interface MenuMaestroRepository extends JpaRepository<MenuMaestro, UUID>, JpaSpecificationExecutor<MenuMaestro> {
	@Query("SELECT adg FROM MenuMaestro adg "
			+ " WHERE adg.menuMaestro is null and adg.habilitado is true order by adg.orden")
	List<MenuMaestro> findByMenuMaestro();

	@Query("SELECT adg FROM MenuMaestro adg "
			+ " WHERE adg.menuMaestro.menuMaestroId = :menuReferenciaId and adg.habilitado is true order by adg.orden")
	List<MenuMaestro> findByMenuOpcion(@Param("menuReferenciaId") UUID menuReferenciaId);

	@Query("SELECT MAX(m.orden)FROM MenuMaestro m WHERE m.menuMaestro IS NULL")
	BigDecimal getOrden();

	@Query("SELECT MAX(m.orden) FROM MenuMaestro m WHERE m.menuMaestro.menuMaestroId = :referenciaId")
	BigDecimal getOrdenItem(@Param("referenciaId") UUID referenciaId);

}
