package com.soa.soaventas.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "999999.99", message = "El precio no puede exceder 999999.99")
    private BigDecimal precio;

    @NotBlank(message = "La categoría es obligatoria")
    @Pattern(regexp = "pollo|papas|bebidas|ensaladas|salsas", 
             message = "Categoría no válida. Opciones: pollo, papas, bebidas, ensaladas, salsas")
    private String categoria;

    private Boolean disponible = true;


    private String imagenUrl;
}