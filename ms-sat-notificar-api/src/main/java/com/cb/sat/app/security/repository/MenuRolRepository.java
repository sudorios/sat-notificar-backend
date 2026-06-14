package com.cb.sat.app.security.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cb.sat.domain.security.MenuMaestro;
import com.cb.sat.domain.security.MenuRol;
import com.cb.sat.domain.security.Rol;

public interface MenuRolRepository extends JpaRepository<MenuRol, UUID>, JpaSpecificationExecutor<MenuRol> {
	@Query("SELECT adg FROM MenuRol adg   WHERE adg.menuMaestro.menuMaestroId = :menuMaestroId and adg.rol.rolId = :rolId")
	MenuRol findByMenuPerfil(@Param("rolId") UUID rolId, @Param("menuMaestroId") UUID menuMaestroId);

	@Query("SELECT adg FROM MenuRol adg  WHERE adg.menuRolId = :menuRolId and adg.rol.rolId = :rolId ")
	MenuRol findByMenuPerfilUsuario(@Param("menuRolId") UUID menuRolId, @Param("rolId") UUID rolId);

	Optional<MenuRol> findByRolAndMenuMaestro(Rol rol, MenuMaestro menuMaestro);
}
