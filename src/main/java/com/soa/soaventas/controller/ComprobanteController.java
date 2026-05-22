package com.soa.soaventas.controller;

import com.soa.soaventas.dto.request.ComprobanteRequest;
import com.soa.soaventas.dto.response.ApiResponse;
import com.soa.soaventas.dto.response.ComprobanteResponse;
import com.soa.soaventas.service.ComprobanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comprobantes")
@RequiredArgsConstructor
@Tag(name = "Comprobantes", description = "API para gestión de comprobantes (Boletas/Facturas)")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    @PostMapping
    @Operation(summary = "Generar un nuevo comprobante")
    public ResponseEntity<ApiResponse<ComprobanteResponse>> generar(@Valid @RequestBody ComprobanteRequest request) {
        ComprobanteResponse response = comprobanteService.generarComprobante(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Comprobante generado exitosamente", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener comprobante por ID")
    public ResponseEntity<ApiResponse<ComprobanteResponse>> obtenerPorId(@PathVariable UUID id) {
        ComprobanteResponse response = comprobanteService.obtenerComprobantePorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/pago/{pagoId}")
    @Operation(summary = "Obtener comprobante por ID de pago")
    public ResponseEntity<ApiResponse<ComprobanteResponse>> obtenerPorPago(@PathVariable UUID pagoId) {
        ComprobanteResponse response = comprobanteService.obtenerComprobantePorPago(pagoId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}/pdf")
    @Operation(summary = "Descargar comprobante en PDF")
    public ResponseEntity<byte[]> descargarPDF(@PathVariable UUID id) {
        byte[] pdf = comprobanteService.generarPDF(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprobante_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}