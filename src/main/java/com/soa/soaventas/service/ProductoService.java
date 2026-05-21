package com.soa.soaventas.service;

import java.util.List;
import java.util.UUID;

import com.soa.soaventas.dto.request.ProductoRequest;
import com.soa.soaventas.dto.response.ProductoResponse;

public interface ProductoService {
    ProductoResponse crearProducto(ProductoRequest request);
    ProductoResponse actualizarProducto(UUID id, ProductoRequest request);
    void eliminarProducto(UUID id);
    ProductoResponse obtenerProductoPorId(UUID id);
    List<ProductoResponse> listarProductosDisponibles();
}
