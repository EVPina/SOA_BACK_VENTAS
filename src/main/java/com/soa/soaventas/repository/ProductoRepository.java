package com.soa.soaventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.soaventas.model.Producto;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    List<Producto> findByDisponibleTrue();
    boolean existsByNombreIgnoreCase(String nombre);
}
