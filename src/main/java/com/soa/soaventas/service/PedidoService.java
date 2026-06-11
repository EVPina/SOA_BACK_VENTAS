package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.PedidoRequest;
import com.soa.soaventas.dto.response.PedidoResponse;
import com.soa.soaventas.dto.response.ReporteVentasResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PedidoService {
    PedidoResponse crearPedido(PedidoRequest request);
    PedidoResponse actualizarEstado(UUID id, String estado);
    PedidoResponse obtenerPedidoPorId(UUID id);
    List<PedidoResponse> listarPedidosPorEstado(String estado);
    List<PedidoResponse> listarPedidosPorCliente(UUID clienteId);
    List<PedidoResponse> listarPedidosActivosPorMesa(UUID sesionMesaId);
    ReporteVentasResponse obtenerResumenVentasPorFecha(LocalDate fecha);
    void cancelarPedido(UUID id);
    public BigDecimal obtenerTotalAcumuladoPorCliente(UUID clienteId);
}