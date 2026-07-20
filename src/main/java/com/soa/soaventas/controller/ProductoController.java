package com.soa.soaventas.controller;

import com.soa.soaventas.dto.request.ProductoRequest;
import com.soa.soaventas.dto.response.ApiResponse;
import com.soa.soaventas.dto.response.ProductoResponse;
import com.soa.soaventas.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<ApiResponse<ProductoResponse>> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.crearProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto creado exitosamente", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<ApiResponse<ProductoResponse>> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.actualizarProducto(id, request);
        return ResponseEntity.ok(ApiResponse.success("Producto actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto (soft delete, lo marca como no disponible)")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok(ApiResponse.success("Producto eliminado exitosamente", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID")
    public ResponseEntity<ApiResponse<ProductoResponse>> obtenerPorId(@PathVariable UUID id) {
        ProductoResponse response = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar productos disponibles (alias para compatibilidad con menú)")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> listarActivos() {
        List<ProductoResponse> response = productoService.listarProductosDisponibles();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar productos disponibles")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> listarDisponibles() {
        List<ProductoResponse> response = productoService.listarProductosDisponibles();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/paginados")
    @Operation(summary = "Listar todos los productos paginados")
    public ResponseEntity<ApiResponse<Page<ProductoResponse>>> listarPaginados(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductoResponse> response = productoService.listarProductosPaginados(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> buscarPorNombre(
            @RequestParam String nombre) {
        List<ProductoResponse> response = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar productos por categoría")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> buscarPorCategoria(@PathVariable String categoria) {
        List<ProductoResponse> response = productoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/precio")
    @Operation(summary = "Buscar productos disponibles por rango de precio")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> buscarPorRangoPrecio(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<ProductoResponse> response = productoService.buscarDisponiblesPorRangoPrecio(min, max);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{id}/disponibilidad")
    @Operation(summary = "Actualizar disponibilidad de un producto")
    public ResponseEntity<ApiResponse<Void>> actualizarDisponibilidad(
            @PathVariable UUID id,
            @RequestParam Boolean disponible) {
        productoService.actualizarDisponibilidad(id, disponible);
        return ResponseEntity.ok(ApiResponse.success("Disponibilidad actualizada exitosamente", null));
    }

    @GetMapping("/contar/disponibles")
    @Operation(summary = "Contar productos disponibles")
    public ResponseEntity<ApiResponse<Long>> contarDisponibles() {
        long cantidad = productoService.contarDisponibles();
        return ResponseEntity.ok(ApiResponse.success(cantidad));
    }
}
