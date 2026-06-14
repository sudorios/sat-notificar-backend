package com.cb.sat.app.configuration.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.cb.sat.domain.configuration.Catalogo;

public interface CatalogoRepository extends JpaRepository<Catalogo, UUID>, JpaSpecificationExecutor<Catalogo> {

	@Query("SELECT max(t.orden) FROM  Catalogo t where t.referenciaCodigo = ?1")
	BigDecimal getCatalogoOrden(String referenciaCodigo);

	@Query("SELECT t FROM Catalogo t WHERE t.codigo=:codigo")
	Optional<Catalogo> getByCodigo(String codigo);

	@Query("SELECT max(t.orden) FROM  Catalogo t where t.referenciaCodigo is null")
	BigDecimal getCatalogoOrden();

	List<Catalogo> findByCodigo(String codigo);

	List<Catalogo> findByPrefijo(String prefijo);
}
