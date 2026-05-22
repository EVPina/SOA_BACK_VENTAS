package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.PagoRequest;
import com.soa.soaventas.dto.response.PagoResponse;
import com.soa.soaventas.exception.BusinessException;
import com.soa.soaventas.exception.ResourceNotFoundException;
import com.soa.soaventas.mapper.PagoMapper;
import com.soa.soaventas.model.Pago;
import com.soa.soaventas.model.Pedido;
import com.soa.soaventas.repository.PagoRepository;
import com.soa.soaventas.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagoMapper pagoMapper;

    @Override
    public PagoResponse registrarPago(PagoRequest request) {
        log.info("Registrando pago para pedido: {}", request.getPedidoId());
        
        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        
        // Validar que no esté pagado
        if ("PAGADO".equals(pedido.getEstado())) {
            throw new BusinessException("El pedido ya está pagado");
        }
        
        // Validar monto
        if (request.getMonto().compareTo(pedido.getTotal()) < 0) {
            throw new BusinessException("El monto es menor al total del pedido");
        }
        
        Pago pago = pagoMapper.toEntity(request);
        pago.setPedido(pedido);
        
        Pago savedPago = pagoRepository.save(pago);
        
        // Actualizar estado del pedido
        pedido.setEstado("PAGADO");
        pedidoRepository.save(pedido);
        
        return pagoMapper.toResponse(savedPago);
    }

    @Override
    public PagoResponse obtenerPagoPorId(UUID id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id));
        return pagoMapper.toResponse(pago);
    }

    @Override
    public List<PagoResponse> listarPagosPorPedido(UUID pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId)
                .stream()
                .map(pagoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoResponse> listarPagosPorMetodo(String metodoPago) {
        return pagoRepository.findByMetodoPago(metodoPago)
                .stream()
                .map(pagoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void reembolsarPago(UUID id) {
        log.info("Reembolsando pago: {}", id);
        
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id));
        
        pago.setEstado("REEMBOLSADO");
        pagoRepository.save(pago);
    }
}