package com.soa.soaventas.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class ComprobanteRequest {
    @NotNull(message = "El ID del pago es obligatorio")
    private UUID pagoId;

    @NotBlank(message = "El tipo de comprobante es obligatorio")
    @Pattern(regexp = "BOLETA|FACTURA", message = "Tipo debe ser BOLETA o FACTURA")
    private String tipo;

    @NotNull(message = "El número es obligatorio")
    @Min(value = 1, message = "El número debe ser mayor a 0")
    private Integer numero;

    @NotBlank(message = "La serie es obligatoria")
    @Pattern(regexp = "B001|F001", message = "Serie debe ser B001 o F001")
    private String serie;

    @Pattern(regexp = "\\d{11}", message = "RUC debe tener 11 dígitos")
    private String ruc;

    private String razonSocial;
}