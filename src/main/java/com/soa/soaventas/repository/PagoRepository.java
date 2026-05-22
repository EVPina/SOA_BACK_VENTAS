package com.soa.soaventas.repository;

import com.soa.soaventas.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PagoRepository extends JpaRepository<Pago, UUID> {
    
    List<Pago> findByPedidoId(UUID pedidoId);
    
    List<Pago> findByMetodoPago(String metodoPago);
    
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.pedido.id = :pedidoId")
    BigDecimal sumMontoByPedidoId(@Param("pedidoId") UUID pedidoId);
    
    @Query("SELECT p FROM Pago p WHERE p.createdAt BETWEEN :inicio AND :fin")
    List<Pago> findPagosByFechaRange(@Param("inicio") LocalDateTime inicio, 
                                      @Param("fin") LocalDateTime fin);
}