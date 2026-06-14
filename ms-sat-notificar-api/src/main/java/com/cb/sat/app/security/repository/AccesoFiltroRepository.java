package com.cb.sat.app.security.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cb.sat.domain.security.view.AccesoDataGrid;

public interface AccesoFiltroRepository
		extends JpaRepository<AccesoDataGrid, UUID>, JpaSpecificationExecutor<AccesoDataGrid> {
	@Query("SELECT adg FROM AccesoDataGrid adg  WHERE adg.rolId = :rolId and adg.menuMaestroId = :menuMaestroId")
	List<AccesoDataGrid> findByMenuPerfil(@Param("rolId") UUID rolId, @Param("menuMaestroId") UUID menuMaestroId);

	@Query("SELECT count(adg) FROM AccesoDataGrid adg  WHERE adg.rolId = :rolId")
	BigDecimal validarConfigPerfil(@Param("rolId") UUID rolId);

}
