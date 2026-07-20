package com.soa.soaventas.controller;

import com.soa.soaventas.dto.request.PagoRequest;
import com.soa.soaventas.dto.response.ApiResponse;
import com.soa.soaventas.dto.response.PagoResponse;
import com.soa.soaventas.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "API para gestión de pagos")
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago")
    public ResponseEntity<ApiResponse<PagoResponse>> registrar(@Valid @RequestBody PagoRequest request) {
        PagoResponse response = pagoService.registrarPago(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pago registrado exitosamente", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID")
    public ResponseEntity<ApiResponse<PagoResponse>> obtenerPorId(@PathVariable UUID id) {
        PagoResponse response = pagoService.obtenerPagoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Listar pagos por pedido")
    public ResponseEntity<ApiResponse<List<PagoResponse>>> listarPorPedido(@PathVariable UUID pedidoId) {
        List<PagoResponse> response = pagoService.listarPagosPorPedido(pedidoId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/metodo/{metodoPago}")
    @Operation(summary = "Listar pagos por método de pago")
    public ResponseEntity<ApiResponse<List<PagoResponse>>> listarPorMetodo(@PathVariable String metodoPago) {
        List<PagoResponse> response = pagoService.listarPagosPorMetodo(metodoPago);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/reembolsar")
    @Operation(summary = "Reembolsar un pago")
    public ResponseEntity<ApiResponse<Void>> reembolsar(@PathVariable UUID id) {
        pagoService.reembolsarPago(id);
        return ResponseEntity.ok(ApiResponse.success("Pago reembolsado exitosamente", null));
    }
}
