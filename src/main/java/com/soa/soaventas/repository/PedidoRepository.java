package com.soa.soaventas.repository;

import com.soa.soaventas.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    
    List<Pedido> findByEstado(String estado);
    
    List<Pedido> findByClienteId(UUID clienteId);
    
    Page<Pedido> findByOrigen(String origen, Pageable pageable);
    
    @Query("SELECT p FROM Pedido p WHERE p.createdAt BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findPedidosByFechaRange(@Param("fechaInicio") LocalDateTime inicio, 
                                          @Param("fechaFin") LocalDateTime fin);
    
    @Query("SELECT p FROM Pedido p WHERE p.sesionMesaId = :sesionMesaId AND p.estado != 'PAGADO'")
    List<Pedido> findPedidosActivosByMesa(@Param("sesionMesaId") UUID sesionMesaId);
    
    long countByEstadoAndCreatedAtBetween(String estado, LocalDateTime inicio, LocalDateTime fin);
}