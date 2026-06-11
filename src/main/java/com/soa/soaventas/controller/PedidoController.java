package com.soa.soaventas.controller;

import com.soa.soaventas.dto.request.PedidoRequest;
import com.soa.soaventas.dto.response.ApiResponse;
import com.soa.soaventas.dto.response.PedidoResponse;
import com.soa.soaventas.dto.response.ReporteVentasResponse;
import com.soa.soaventas.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "API para gestión de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> crear(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pedido creado exitosamente", response));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de un pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> actualizarEstado(
            @PathVariable UUID id, @RequestParam String estado) {
        PedidoResponse response = pedidoService.actualizarEstado(id, estado);
        return ResponseEntity.ok(ApiResponse.success("Estado actualizado exitosamente", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID")
    public ResponseEntity<ApiResponse<PedidoResponse>> obtenerPorId(@PathVariable UUID id) {
        PedidoResponse response = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar pedidos por estado")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarPorEstado(@PathVariable String estado) {
        List<PedidoResponse> response = pedidoService.listarPedidosPorEstado(estado);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarPorCliente(@PathVariable UUID clienteId) {
        List<PedidoResponse> response = pedidoService.listarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/mesa/{sesionMesaId}/activos")
    @Operation(summary = "Listar pedidos activos por mesa")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarActivosPorMesa(@PathVariable UUID sesionMesaId) {
        List<PedidoResponse> response = pedidoService.listarPedidosActivosPorMesa(sesionMesaId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/reporte/ventas")
    @Operation(summary = "Obtener resumen de ventas por fecha")
    public ResponseEntity<ApiResponse<ReporteVentasResponse>> obtenerResumenVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        ReporteVentasResponse response = pedidoService.obtenerResumenVentasPorFecha(fecha);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar un pedido")
    public ResponseEntity<ApiResponse<Void>> cancelar(@PathVariable UUID id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.ok(ApiResponse.success("Pedido cancelado exitosamente", null));
    }

    // Agregar en PedidoController.java
    @GetMapping("/total/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<BigDecimal>> obtenerTotalAcumulado(@PathVariable UUID clienteId) {
        BigDecimal total = pedidoService.obtenerTotalAcumuladoPorCliente(clienteId);
        return ResponseEntity.ok(ApiResponse.success(total));
    }
}