package com.cb.sat.app.security.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cb.sat.domain.security.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {

}
