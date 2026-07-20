package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.ProductoRequest;
import com.soa.soaventas.dto.response.ProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductoService {

    // CRUD básico
    ProductoResponse crearProducto(ProductoRequest request);
    ProductoResponse actualizarProducto(UUID id, ProductoRequest request);
    void eliminarProducto(UUID id);
    ProductoResponse obtenerProductoPorId(UUID id);

    // Listados
    List<ProductoResponse> listarProductosDisponibles();
    Page<ProductoResponse> listarProductosPaginados(Pageable pageable);

    // Búsquedas
    List<ProductoResponse> buscarPorNombre(String nombre);
    List<ProductoResponse> buscarPorCategoria(String categoria);
    List<ProductoResponse> buscarDisponiblesPorRangoPrecio(BigDecimal min, BigDecimal max);

    // Acciones específicas
    void actualizarDisponibilidad(UUID id, Boolean disponible);
    long contarDisponibles();
}