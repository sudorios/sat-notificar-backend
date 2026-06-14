package com.cb.sat.domain.security.mappers;

import com.cb.sat.domain.security.Usuario;
import com.cb.sat.domain.security.view.UsuarioDataGrid;
import com.cb.sat.dto.model.usuario.UsuarioDataGridResponse;
import com.cb.sat.dto.model.usuario.UsuarioFindResponse;
import com.cb.sat.dto.model.usuario.UsuarioRequest;
import com.cb.sat.dto.model.usuario.UsuarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    Usuario map(UsuarioRequest request);

    UsuarioFindResponse map(UsuarioDataGrid tt);

    UsuarioResponse map(Usuario u);

    UsuarioDataGridResponse mapUsuario(UsuarioDataGrid u);
}
