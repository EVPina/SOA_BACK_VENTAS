package com.soa.soaventas.repository;

import com.soa.soaventas.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, UUID> {
    
    List<DetallePedido> findByPedidoId(UUID pedidoId);
    
    @Query("SELECT d.producto.id, SUM(d.cantidad) FROM DetallePedido d WHERE d.pedido.id = :pedidoId GROUP BY d.producto.id")
    List<Object[]> findProductosMasVendidosPorPedido(@Param("pedidoId") UUID pedidoId);
}