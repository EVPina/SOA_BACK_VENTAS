package com.soa.soaventas.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class PedidoRequest {
    @NotNull(message = "El ID de sesión de mesa es obligatorio")
    private UUID sesionMesaId;

    @NotNull(message = "El ID del cliente es obligatorio")
    private UUID clienteId;

    private UUID usuarioTomoId;

    @NotBlank(message = "El origen es obligatorio")
    @Pattern(regexp = "QR|MOZO", message = "Origen debe ser QR o MOZO")
    private String origen;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    private List<DetallePedidoRequest> detalles;

    @Data
    public static class DetallePedidoRequest {
        @NotNull(message = "El ID del producto es obligatorio")
        private UUID productoId;

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad mínima es 1")
        private Integer cantidad;

        private String notas;
    }
}