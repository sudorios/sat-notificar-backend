package com.cb.sat.app.security.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cb.sat.domain.security.view.MenuRolDataGrid;

public interface MenuRolFiltroRepository
		extends JpaRepository<MenuRolDataGrid, UUID>, JpaSpecificationExecutor<MenuRolDataGrid> {

}
