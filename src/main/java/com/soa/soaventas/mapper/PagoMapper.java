package com.soa.soaventas.mapper;

import com.soa.soaventas.dto.request.PagoRequest;
import com.soa.soaventas.dto.response.PagoResponse;
import com.soa.soaventas.model.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "COMPLETADO")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "comprobante", ignore = true)
    Pago toEntity(PagoRequest request);
    
    @Mapping(source = "pedido.id", target = "pedidoId")
    PagoResponse toResponse(Pago pago);
    
    void updateEntity(@MappingTarget Pago pago, PagoRequest request);
}