package com.soa.soaventas.repository;

import com.soa.soaventas.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    // Removidas búsquedas por estado ya que no existe la columna
    
    // Productos disponibles (independiente del estado)
    List<Producto> findByDisponibleTrue();
    
    // Buscar por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar por categoría
    List<Producto> findByCategoria(String categoria);
    
    // Verificar si existe por nombre
    boolean existsByNombreIgnoreCase(String nombre);
    
    // Contar productos disponibles
    long countByDisponibleTrue();
    
    // Productos disponibles paginados
    Page<Producto> findByDisponibleTrue(Pageable pageable);
    
    // Buscar productos disponibles por rango de precio
    @Query("SELECT p FROM Producto p WHERE p.disponible = true AND p.precio BETWEEN :min AND :max")
    List<Producto> findDisponiblesByPrecioRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
}