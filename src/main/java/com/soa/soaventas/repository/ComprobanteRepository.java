package com.soa.soaventas.repository;

import com.soa.soaventas.model.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, UUID> {
    
    Optional<Comprobante> findByPagoId(UUID pagoId);
    
    Optional<Comprobante> findBySerieAndNumero(String serie, Integer numero);
    
    boolean existsBySerieAndNumero(String serie, Integer numero);
}