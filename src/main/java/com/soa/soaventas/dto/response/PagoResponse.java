package com.soa.soaventas.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PagoResponse {
    private UUID id;
    private UUID pedidoId;
    private String metodoPago;
    private BigDecimal monto;
    private String referencia;
    private String estado;
    private LocalDateTime createdAt;
    private ComprobanteResponse comprobante;
}