package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.ComprobanteRequest;
import com.soa.soaventas.dto.response.ComprobanteResponse;
import com.soa.soaventas.exception.BusinessException;
import com.soa.soaventas.exception.ResourceNotFoundException;
import com.soa.soaventas.mapper.ComprobanteMapper;
import com.soa.soaventas.model.Comprobante;
import com.soa.soaventas.model.Pago;
import com.soa.soaventas.repository.ComprobanteRepository;
import com.soa.soaventas.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ComprobanteServiceImpl implements ComprobanteService {

    private final ComprobanteRepository comprobanteRepository;
    private final PagoRepository pagoRepository;
    private final ComprobanteMapper comprobanteMapper;

    @Override
    public ComprobanteResponse generarComprobante(ComprobanteRequest request) {
        log.info("Generando comprobante para pago: {}", request.getPagoId());
        
        // Validar que no exista comprobante para este pago
        if (comprobanteRepository.findByPagoId(request.getPagoId()).isPresent()) {
            throw new BusinessException("Ya existe un comprobante para este pago");
        }
        
        // Validar serie y número únicos
        if (comprobanteRepository.existsBySerieAndNumero(request.getSerie(), request.getNumero())) {
            throw new BusinessException("Ya existe un comprobante con la serie y número especificados");
        }
        
        // Validar RUC para factura
        if ("FACTURA".equals(request.getTipo()) && request.getRuc() == null) {
            throw new BusinessException("Para factura es obligatorio el RUC");
        }
        
        Pago pago = pagoRepository.findById(request.getPagoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        
        Comprobante comprobante = comprobanteMapper.toEntity(request);
        comprobante.setPago(pago);
        
        return comprobanteMapper.toResponse(comprobanteRepository.save(comprobante));
    }

    @Override
    public ComprobanteResponse obtenerComprobantePorId(UUID id) {
        Comprobante comprobante = comprobanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprobante no encontrado: " + id));
        return comprobanteMapper.toResponse(comprobante);
    }

    @Override
    public ComprobanteResponse obtenerComprobantePorPago(UUID pagoId) {
        Comprobante comprobante = comprobanteRepository.findByPagoId(pagoId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe comprobante para el pago: " + pagoId));
        return comprobanteMapper.toResponse(comprobante);
    }

    @Override
    public byte[] generarPDF(UUID id) {
        // TODO: Implementar generación de PDF con iText o JasperReports
        log.info("Generando PDF para comprobante: {}", id);
        return new byte[0];
    }
}