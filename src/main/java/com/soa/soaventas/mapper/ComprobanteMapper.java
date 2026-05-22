package com.soa.soaventas.mapper;

import com.soa.soaventas.dto.request.ComprobanteRequest;
import com.soa.soaventas.dto.response.ComprobanteResponse;
import com.soa.soaventas.model.Comprobante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ComprobanteMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "pago", ignore = true)
    Comprobante toEntity(ComprobanteRequest request);
    
    @Mapping(source = "pago.id", target = "pagoId")
    ComprobanteResponse toResponse(Comprobante comprobante);
    
    void updateEntity(@MappingTarget Comprobante comprobante, ComprobanteRequest request);
}