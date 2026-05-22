package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.PedidoRequest;
import com.soa.soaventas.dto.response.PedidoResponse;
import com.soa.soaventas.dto.response.ReporteVentasResponse;
import com.soa.soaventas.exception.BusinessException;
import com.soa.soaventas.exception.ResourceNotFoundException;
import com.soa.soaventas.mapper.PedidoMapper;
import com.soa.soaventas.model.DetallePedido;
import com.soa.soaventas.model.Pedido;
import com.soa.soaventas.model.Producto;
import com.soa.soaventas.repository.DetallePedidoRepository;
import com.soa.soaventas.repository.PedidoRepository;
import com.soa.soaventas.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    public PedidoResponse crearPedido(PedidoRequest request) {
        log.info("Creando nuevo pedido para cliente: {}", request.getClienteId());
        
        // Crear pedido
        Pedido pedido = pedidoMapper.toEntity(request);
        Pedido savedPedido = pedidoRepository.save(pedido);
        
        // Agregar detalles
        BigDecimal subtotal = BigDecimal.ZERO;
        for (PedidoRequest.DetallePedidoRequest detalleReq : request.getDetalles()) {
            Producto producto = productoRepository.findById(detalleReq.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + detalleReq.getProductoId()));
            
            if (!producto.getDisponible()) {
                throw new BusinessException("Producto no disponible: " + producto.getNombre());
            }
            
            DetallePedido detalle = DetallePedido.builder()
                    .pedido(savedPedido)
                    .producto(producto)
                    .cantidad(detalleReq.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .notas(detalleReq.getNotas())
                    .build();
            detalle.calcularSubtotal();
            
            subtotal = subtotal.add(detalle.getSubtotal());
            detallePedidoRepository.save(detalle);
        }
        
        // Actualizar totales
        BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"));
        savedPedido.setSubtotal(subtotal);
        savedPedido.setIgv(igv);
        savedPedido.setTotal(subtotal.add(igv));
        
        return pedidoMapper.toResponse(pedidoRepository.save(savedPedido));
    }

    @Override
    public PedidoResponse actualizarEstado(UUID id, String estado) {
        log.info("Actualizando estado del pedido {} a {}", id, estado);
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
        
        pedido.setEstado(estado);
        return pedidoMapper.toResponse(pedidoRepository.save(pedido));
    }

    @Override
    public PedidoResponse obtenerPedidoPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public List<PedidoResponse> listarPedidosPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado)
                .stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponse> listarPedidosPorCliente(UUID clienteId) {
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponse> listarPedidosActivosPorMesa(UUID sesionMesaId) {
        return pedidoRepository.findPedidosActivosByMesa(sesionMesaId)
                .stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReporteVentasResponse obtenerResumenVentasPorFecha(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);
        
        List<Pedido> pedidos = pedidoRepository.findPedidosByFechaRange(inicio, fin);
        
        BigDecimal ventasTotales = pedidos.stream()
                .map(Pedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal ventasQR = pedidos.stream()
                .filter(p -> "QR".equals(p.getOrigen()))
                .map(Pedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal ventasMozo = pedidos.stream()
                .filter(p -> "MOZO".equals(p.getOrigen()))
                .map(Pedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        long pedidosPagados = pedidos.stream()
                .filter(p -> "PAGADO".equals(p.getEstado()))
                .count();
        
        return ReporteVentasResponse.builder()
                .fecha(fecha)
                .totalPedidos((long) pedidos.size())
                .ventasTotales(ventasTotales)
                .ventasQR(ventasQR)
                .ventasMozo(ventasMozo)
                .pedidosPagados(pedidosPagados)
                .build();
    }

    @Override
    public void cancelarPedido(UUID id) {
        log.info("Cancelando pedido: {}", id);
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
        
        if ("PAGADO".equals(pedido.getEstado())) {
            throw new BusinessException("No se puede cancelar un pedido ya pagado");
        }
        
        pedido.setEstado("CANCELADO");
        pedidoRepository.save(pedido);
    }
}