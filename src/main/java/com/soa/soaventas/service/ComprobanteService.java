package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.ComprobanteRequest;
import com.soa.soaventas.dto.response.ComprobanteResponse;
import java.util.UUID;

public interface ComprobanteService {
    ComprobanteResponse generarComprobante(ComprobanteRequest request);
    ComprobanteResponse obtenerComprobantePorId(UUID id);
    ComprobanteResponse obtenerComprobantePorPago(UUID pagoId);
    byte[] generarPDF(UUID id);
}