package com.cb.sat.app.security.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cb.sat.domain.security.Acceso;
import com.cb.sat.domain.security.view.AccesoDataGrid;

public interface AccesoRepository extends JpaRepository<Acceso, UUID>, JpaSpecificationExecutor<Acceso> {
	@Modifying
	@Query("delete from Acceso b where b.rol.rolId =:rolId")
	void deleteAcceso(@Param("rolId") UUID rolId);

	@Modifying
	@Query("delete from Acceso b where b.menuOpcion.menuOpcionId = :menuOpcionId and b.rol.rolId =:rolId")
	void deleteAccesoxMenu(@Param("menuOpcionId") UUID menuOpcionId, @Param("rolId") UUID rolId);

	@Query("SELECT t FROM AccesoDataGrid t WHERE t.menuMaestroId = :menuId and t.rolCodigo = :rolCodigo")
	List<AccesoDataGrid> getAccesoMenu(@Param("menuId") UUID menuId, @Param("rolCodigo") String rolCodigo);

}
