package com.soa.soaventas.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProductoResponse {

    private UUID id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private Boolean disponible;
    private String imagenUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}