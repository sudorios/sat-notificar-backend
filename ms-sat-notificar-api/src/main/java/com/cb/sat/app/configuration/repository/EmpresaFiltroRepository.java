package com.cb.sat.app.configuration.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cb.sat.domain.configuration.view.EmpresaDataGrid;

@Repository
public interface EmpresaFiltroRepository
		extends JpaRepository<EmpresaDataGrid, UUID>, JpaSpecificationExecutor<EmpresaDataGrid> {

}