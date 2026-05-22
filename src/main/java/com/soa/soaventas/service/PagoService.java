package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.PagoRequest;
import com.soa.soaventas.dto.response.PagoResponse;
import java.util.List;
import java.util.UUID;

public interface PagoService {
    PagoResponse registrarPago(PagoRequest request);
    PagoResponse obtenerPagoPorId(UUID id);
    List<PagoResponse> listarPagosPorPedido(UUID pedidoId);
    List<PagoResponse> listarPagosPorMetodo(String metodoPago);
    void reembolsarPago(UUID id);
}