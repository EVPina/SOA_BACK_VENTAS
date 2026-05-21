package com.soa.soaventas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soa.soaventas.dto.request.ProductoRequest;
import com.soa.soaventas.dto.response.ApiResponse;
import com.soa.soaventas.dto.response.ProductoResponse;
import com.soa.soaventas.service.ProductoService;

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
    @Operation(summary = "Actualizar un producto")
    public ResponseEntity<ApiResponse<ProductoResponse>> actualizar(
            @PathVariable UUID id, @Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.actualizarProducto(id, request);
        return ResponseEntity.ok(ApiResponse.success("Producto actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok(ApiResponse.success("Producto eliminado exitosamente", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ApiResponse<ProductoResponse>> obtenerPorId(@PathVariable UUID id) {
        ProductoResponse response = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar productos disponibles")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> listarDisponibles() {
        List<ProductoResponse> response = productoService.listarProductosDisponibles();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
