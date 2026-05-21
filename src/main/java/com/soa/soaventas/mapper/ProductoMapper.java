package com.soa.soaventas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.soa.soaventas.dto.request.ProductoRequest;
import com.soa.soaventas.dto.response.ProductoResponse;
import com.soa.soaventas.model.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    Producto toEntity(ProductoRequest request);
    ProductoResponse toResponse(Producto producto);
    void updateEntity(ProductoRequest request, @MappingTarget Producto producto);
}
