package com.cb.sat.domain.configuration.mappers;

import com.cb.sat.domain.configuration.Empresa;
import com.cb.sat.domain.configuration.view.EmpresaDataGrid;
import com.cb.sat.dto.model.empresa.EmpresaDataGridResponse;
import com.cb.sat.dto.model.empresa.EmpresaRequest;
import com.cb.sat.dto.model.empresa.EmpresaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmpresaMapper {

    Empresa map(EmpresaRequest t);

    EmpresaResponse map(Empresa t);

    EmpresaDataGridResponse map(EmpresaDataGrid t);
}
