package com.soa.soaventas.mapper;

import com.soa.soaventas.dto.request.PedidoRequest;
import com.soa.soaventas.dto.response.DetallePedidoResponse;
import com.soa.soaventas.dto.response.PedidoResponse;
import com.soa.soaventas.model.DetallePedido;
import com.soa.soaventas.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface PedidoMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "PENDIENTE")
    @Mapping(target = "subtotal", constant = "0")
    @Mapping(target = "igv", constant = "0")
    @Mapping(target = "total", constant = "0")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    Pedido toEntity(PedidoRequest request);
    
    PedidoResponse toResponse(Pedido pedido);
    
    DetallePedidoResponse toDetalleResponse(DetallePedido detalle);
    
    void updateEntity(@MappingTarget Pedido pedido, PedidoRequest request);
}