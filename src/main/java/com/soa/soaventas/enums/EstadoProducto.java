package com.soa.soaventas.enums;

public enum EstadoProducto {
    ACTIVO("Activo", "Producto disponible para venta"),
    INACTIVO("Inactivo", "Producto no disponible temporalmente"),
    AGOTADO("Agotado", "Producto sin stock"),
    ELIMINADO("Eliminado", "Producto dado de baja");

    private final String nombre;
    private final String descripcion;

    EstadoProducto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoProducto fromString(String text) {
        for (EstadoProducto estado : EstadoProducto.values()) {
            if (estado.name().equalsIgnoreCase(text)) {
                return estado;
            }
        }
        return ACTIVO;
    }
}