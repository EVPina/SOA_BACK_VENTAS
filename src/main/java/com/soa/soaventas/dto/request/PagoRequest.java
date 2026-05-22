package com.soa.soaventas.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PagoRequest {
    @NotNull(message = "El ID del pedido es obligatorio")
    private UUID pedidoId;

    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "EFECTIVO|TARJETA|YAPE|PLIN|QR", message = "Método de pago no válido")
    private String metodoPago;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto mínimo es 0.01")
    private BigDecimal monto;

    private String referencia;
}