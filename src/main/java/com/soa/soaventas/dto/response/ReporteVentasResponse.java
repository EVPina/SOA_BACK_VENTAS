package com.soa.soaventas.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ReporteVentasResponse {
    private LocalDate fecha;
    private Long totalPedidos;
    private BigDecimal ventasTotales;
    private BigDecimal ventasQR;
    private BigDecimal ventasMozo;
    private Long pedidosPagados;
}