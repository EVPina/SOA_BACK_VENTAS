package com.soa.soaventas.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PedidoResponse {
    private UUID id;
    private UUID sesionMesaId;
    private UUID clienteId;
    private UUID usuarioTomoId;
    private String origen;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DetallePedidoResponse> detalles;
    private List<PagoResponse> pagos;
}