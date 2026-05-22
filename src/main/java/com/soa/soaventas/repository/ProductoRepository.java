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

    // Productos disponibles y activos (para mostrar en menú)
    List<Producto> findByDisponibleTrueAndEstado(String estado);
    
    // Productos por estado
    List<Producto> findByEstado(String estado);
    
    // Productos disponibles (independiente del estado)
    List<Producto> findByDisponibleTrue();
    
    // Buscar por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar por categoría y estado
    List<Producto> findByCategoriaAndEstado(String categoria, String estado);
    
    // Verificar si existe por nombre
    boolean existsByNombreIgnoreCase(String nombre);
    
    // Contar productos por estado
    long countByEstado(String estado);
    
    // Productos activos paginados
    Page<Producto> findByEstado(String estado, Pageable pageable);
    
    // Buscar productos activos por rango de precio
    @Query("SELECT p FROM Producto p WHERE p.estado = 'ACTIVO' AND p.precio BETWEEN :min AND :max")
    List<Producto> findActivosByPrecioRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
}